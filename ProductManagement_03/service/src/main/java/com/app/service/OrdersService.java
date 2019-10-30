package com.app.service;

import com.app.converters.CustomersJsonConverter;
import com.app.converters.JsonConverter;
import com.app.converters.OrdersJsonConverter;
import com.app.enums.Category;
import com.app.exception.MyException;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.validators.OrderValidator;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class OrdersService {
    private List<Order> orders;

    public OrdersService(String jsonFilename) {
        orders = getFromJsonFile(jsonFilename);
    }

    public void changeData(String ... filename) {
        this.orders = getFromJsonFile(filename);
    }

    private List<Order> getFromJsonFile(String ... jsonFilenames) {

        OrderValidator orderValidator = new OrderValidator();
        AtomicInteger atomicInteger = new AtomicInteger(1);

        return Arrays
                .stream(jsonFilenames)
                .flatMap(jsonFilename -> new OrdersJsonConverter(jsonFilename).fromJson().orElseThrow(() -> new MyException("cannont convert file " + jsonFilename + " to order")).stream())
                .filter(order -> {
                    Map<String, String> errors = orderValidator.validate(order);

                    if (orderValidator.hasErrors()) {
                        System.out.println("\n\n----------------------------------------------");
                        System.out.println("EXCEPTION IN CAR NO. " + atomicInteger.get());
                        errors.forEach((k, v) -> System.out.println(k + " " + v));
                        System.out.println("----------------------------------------------\n\n");
                    }
                    atomicInteger.incrementAndGet();
                    return !orderValidator.hasErrors();
                }).collect(Collectors.toList());
    }

    public BigDecimal averagePriceForAllOrdersBetweenTwoDates(LocalDate begin, LocalDate end) {
        return orders.stream()
                .filter(order -> order.getEstimatedRealizationDate().compareTo(begin) > 0 && order.getEstimatedRealizationDate().compareTo(end) < 0)
                .map(order -> order.getProduct().getPrice())
                .reduce(ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(orders.size()));
    }

    public LocalDate findDateWithTheMostNumbersOfOrders() {
        return orders.stream()
                .max(Comparator.comparing(Order::getQuantity))
                .map(Order::getEstimatedRealizationDate)
                .orElse(LocalDate.of(2019, 1, 1));
    }

    public LocalDate findDateWithLeastNumbersOfOrders() {
        return orders.stream()
                .min(Comparator.comparing(Order::getQuantity))
                .map(Order::getEstimatedRealizationDate)
                .orElse(LocalDate.of(2019, 1, 1));
    }
    public Category theMostCommonProductCategory() {
        return orders.stream()
                .collect(groupingBy(order -> order.getProduct().getCategory(), counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Map<Category, Order> theMostExpensiveProductForEachCategory() {

        return orders
                .stream()
                .collect(Collectors.groupingBy(
                        o -> o.getProduct().getCategory(),
                        Collectors.collectingAndThen(Collectors
                                .maxBy(Comparator.comparing(o -> o.getProduct().getPrice().multiply(BigDecimal.valueOf(o.getQuantity())))),
                                Optional::orElseThrow)
                ));


    }

    public List<Order> productsOrderedByClient(String clientEmail) {
        var clientOrders = orders
                .stream()
                .peek(order -> System.out.println(clientEmail + " : " + order.getCustomer().getEmail()))
                .filter(o -> o.getCustomer().getEmail().equals(clientEmail))
                .collect(Collectors.toList());
        EmailService emailService = new EmailService();
        emailService.sendAsHtml(clientEmail, "Your orders", clientOrders);
        return clientOrders;
    }

    public BigDecimal totalPriceAfterDiscount() {
        return orders.stream()
                .map(order -> {
                    BigDecimal price = order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));

                    if (order.getCustomer().getAge() < 25) {
                        return price.multiply(new BigDecimal("0.97"));
                    }
                    else if (Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), order.getEstimatedRealizationDate())) <= 2) {
                        return price.multiply(new BigDecimal("0.98"));
                    }

                    return price;
                }).reduce(ZERO, BigDecimal::add);
    }

    public long amountOfClientsWhoOrderedAtLeastNumberOfProducts(int minAmount) {
        List<Customer> customers = orders
                .stream()
                .filter(order -> order.getQuantity() >= minAmount)
                .map(Order::getCustomer)
                .collect(Collectors.toList());
        var customersJsonConverter = new CustomersJsonConverter("customers.json");
        customersJsonConverter.toJson(customers);
        return customers.size();
    }

    public Customer customerWhoPaidMostForOrders() {
        return orders.stream()
                .collect(Collectors.toMap(Order::getCustomer, order -> order.getProduct().getPrice().multiply(new BigDecimal(String.valueOf(order.getQuantity())))))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null)
                .getKey();
    }
    public Map<Month, Integer> ammountOfProductsOrderedInMonth() {
        return orders.stream()
                .collect(groupingBy(order -> order.getEstimatedRealizationDate().getMonth(),
                        Collectors.summingInt(Order::getQuantity)))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,Integer::max, LinkedHashMap::new));
    }
    public Map<Month, Category> mostCommonProductCategoryInMonth() {
        return orders.stream()
                .collect(groupingBy(
                        order -> order.getEstimatedRealizationDate().getMonth(),
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(o -> o.getProduct().getCategory()),
                                map -> map.entrySet()
                                        .stream()
                                        .max(Comparator.comparingInt(e -> e.getValue().size()))
                                        .orElseThrow()
                                        .getKey())));
    }

    @Override
    public String toString() {
        return orders.stream().map(car -> car.toString()).collect(Collectors.joining("\n"));
    }


}
