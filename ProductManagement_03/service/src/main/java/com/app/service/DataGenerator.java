package com.app.service;


import com.app.converters.OrdersJsonConverter;
import com.app.enums.Category;
import com.app.exception.MyException;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class DataGenerator {


    public List<Order> generateOrders(String filename, int size) {
        if (filename == null) {
            throw new MyException("filename - filename is null");
        }
        if (size <= 0) {
            throw new MyException("size - size cannot be null or lower than 0");
        }

        OrdersJsonConverter ordersJsonConverter = new OrdersJsonConverter(filename);
        var orders = IntStream
                .range(0, size)
                .boxed()
                .map(idx -> createOrder())
                .collect(Collectors.toList());
        ordersJsonConverter.toJson(orders);
        return orders;
    }

    private Order createOrder() {
        Customer customer = createCustomer();
        Product product = createProduct();
        LocalDate estimatedRealizationTime = generateOrderEstimatedRealizationDate();
        int quantity = generateOrderQuantity();
        return new Order(customer, product, quantity, estimatedRealizationTime);
    }

    private Customer createCustomer() {
        String name = generateCustomerName();
        String surname = generateCustomerSurname();
        int birthDate = generateCustomerBirthDate();
        String email = generateCustomerEmail();

        return new Customer(name, surname, birthDate, email);
    }

    private Product createProduct() {
        String name = generateProductName();
        BigDecimal price = generateProductPrice();
        Category category = generateProductCategory();
        return new Product(name, price, category);
    }

    private String generateCustomerName() {
        String[] names = {"ADAM", "JANUSZ", "STEFAN"};
        Random rnd = new Random();
        return names[rnd.nextInt(names.length)];
    }

    private String generateCustomerSurname() {
        String[] surnames = {"MAŁYSZ", "AHONEN", "ŻEROMSKI"};
        Random rnd = new Random();
        return surnames[rnd.nextInt(surnames.length)];
    }

    private int generateCustomerBirthDate() {
        int[] birthDates = {19, 24, 32};
        Random rnd = new Random();
        return birthDates[rnd.nextInt(birthDates.length)];
    }

    private String generateCustomerEmail() {
        String[] emails = {"adamMalysz@wp.pl", "wNastepnychZawodachCiePokonam@onet.pl", "ludzieBezdomniNaMaturze@gmail.com"};
        Random rnd = new Random();
        return emails[rnd.nextInt(emails.length)];
    }

    private String generateProductName() {
        String[] names = {"NARTY", "FINLANDIA", "LEKTURA"};
        Random rnd = new Random();
        return names[rnd.nextInt(names.length)];
    }

    private BigDecimal generateProductPrice() {
        BigDecimal[] prices = {new BigDecimal("1000"), new BigDecimal("60"), new BigDecimal("200")};
        Random rnd = new Random();
        return prices[rnd.nextInt(prices.length)];
    }

    private Category generateProductCategory() {
        Category[] categories = {Category.A, Category.B, Category.C};
        Random rnd = new Random();
        return categories[rnd.nextInt(categories.length)];
    }

    private LocalDate generateOrderEstimatedRealizationDate() {
        LocalDate[] estimatedRealizationDates = {LocalDate.of(2019, 10, 11), LocalDate.of(2020, 01, 05), LocalDate.of(2020, 02, 02)};
        Random rnd = new Random();
        return estimatedRealizationDates[rnd.nextInt(estimatedRealizationDates.length)];
    }

    private int generateOrderQuantity() {
        int[] quantities = {2, 14, 3};
        Random rnd = new Random();
        return quantities[rnd.nextInt(quantities.length)];
    }
}
