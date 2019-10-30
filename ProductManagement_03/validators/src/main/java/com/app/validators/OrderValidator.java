package com.app.validators;

import com.app.model.Order;

import java.time.LocalDate;
import java.util.Map;

public class OrderValidator extends AbstractValidator<Order> {

    public final Map<String, String> validate(Order order) {
        errors.clear();

        if (order == null) {
            errors.put("order", "order object is null");
        }
        if (!isCustomerValid(order)) {
            errors.put("order customer", "order customer is not valid" + order.getCustomer());
        }
        if (!isProductValid(order)) {
            errors.put("order product", "order product is not valid" + order.getProduct());
        }
        if (!isQuantityValid(order)) {
            errors.put("order quantity", "order quantity is not valid" + order.getQuantity());
        }
        if (!isEstimatedRealizationDateValid(order)) {
            errors.put("order estimatedRealizationDate", "order estimatedRealizationDate is not valid" + order.getEstimatedRealizationDate());
        }
        return errors;
    }

    private boolean isCustomerValid(Order order) {
        return order.getCustomer() != null;
    }

    private boolean isProductValid(Order order) {
        return order.getProduct() != null;
    }

    private boolean isQuantityValid(Order order) {
        return (order.getQuantity() != null & order.getQuantity() > 0);
    }

    private boolean isEstimatedRealizationDateValid(Order order) {
        LocalDate currentDate = LocalDate.now();
        return (order.getEstimatedRealizationDate() != null & order.getEstimatedRealizationDate().isAfter(currentDate));
    }
}
