package com.app.service;

import com.app.exception.MyException;
import com.app.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;



public class MenuService {

    private final OrdersService ordersService;

    public MenuService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    private void printMenu() {
        System.out.println("\n\n-----------------------------------------");
        System.out.println("1 - show orders");
        System.out.println("2 - average price for orders between two dates");
        System.out.println("3 - date with most numbers of orders");
        System.out.println("4 - date with least numbers of orders");
        System.out.println("5 - the most common product category");
        System.out.println("6 - generate orders");
        System.out.println("7 - products ordered by client");
        System.out.println("8 - total price after discount");
        System.out.println("9 - amount of clients who ordered at least number of products");
        System.out.println("10 - customer who paid most for order");
        System.out.println("11 - ammount of products ordered in given month");
        System.out.println("12 - the most common product category in month");
    }

    public void mainMenu() {
        do {

            try {

                printMenu();
                int option = UserDataService.getInt("Choose option");

                switch (option) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        option4();
                        break;
                    case 5:
                        option5();
                        break;
                    case 6:
                        option6();
                        break;
                    case 7:
                        option7();
                        break;
                    case 8:
                        option8();
                        break;
                    case 9:
                        option9();
                        break;
                    case 10:
                        option10();
                        break;
                    case 11:
                        option11();
                        break;
                    case 12:
                        option12();
                        break;

                    default:
                        System.out.println("Nie ma takiej opcji");

                }

            } catch (MyException e) {
                System.out.println("\n\n------------------- EXCEPTION ---------------");
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------------\n\n");
            }

        } while (true);
    }

private void option1() {

    System.out.println(ordersService);
}
    private void option2() {
        System.out.println(ordersService.averagePriceForAllOrdersBetweenTwoDates(LocalDate.of(2019,01,01),LocalDate.of(2021,12,28)));
    }
    private void option3() {
        System.out.println(ordersService.findDateWithTheMostNumbersOfOrders());
    }
    private void option4() {
        System.out.println(ordersService.findDateWithLeastNumbersOfOrders());
    }
    private void option5() {
        System.out.println(ordersService.theMostCommonProductCategory());
    }
    private void option6() {

        final String FILENAME = "orders.json";
        int size = UserDataService.getInt("Enter amount of elements to generate: ");

        var dataGenerator = new DataGenerator();
        System.out.println("------------------------------------------------");
        System.out.println("----------------- GENERATED DATA ---------------");
        System.out.println("------------------------------------------------");
        System.out.println(toJson(dataGenerator.generateOrders(FILENAME, size)));
        ordersService.changeData(FILENAME);
    }
    private void option7() {
        String email = UserDataService.getString("Enter your email please");
        System.out.println("EMAIL ----> " + email);
        List<Order> yourOrders = new LinkedList<>(ordersService.productsOrderedByClient(email));
        System.out.println(toJson(yourOrders));
    }
    private void option8() {
        System.out.println(ordersService.totalPriceAfterDiscount());
    }
    private void option9() {
        System.out.println(ordersService.amountOfClientsWhoOrderedAtLeastNumberOfProducts(3));
    }

    private void option10() {
        System.out.println(ordersService.customerWhoPaidMostForOrders());
    }
    private void option11() {
        System.out.println(toJson(ordersService.ammountOfProductsOrderedInMonth()));
    }
    private void option12() {
        System.out.println(toJson(ordersService.mostCommonProductCategoryInMonth()));
    }

    private static <T> String toJson(T data) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(data);
        } catch (Exception e) {
            throw new MyException("to json parse in menu service exception");
        }
    }


}


