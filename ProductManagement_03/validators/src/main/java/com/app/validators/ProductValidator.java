package com.app.validators;


import com.app.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public class ProductValidator extends AbstractValidator<Product> {

    public final Map<String, String> validate(Product product) {
        errors.clear();

        if (product == null) {
            errors.put("product", "product object is null");
        }
        if (!isNameValid(product)) {
            errors.put("product name", "product name is not valid :" +product.getName());
        }
        if (!isPriceValid(product)) {
            errors.put("product price", "product price is not valid :" +product.getPrice());
        }
        if (!isCategoryValid(product)){
            errors.put("product category", "product category is not valid :" +product.getCategory());
        }
        return errors;
    }

    private boolean isNameValid(Product product) { return product.getName() != null & product.getName().matches("^[A-Z\\s]+$");}
    private boolean isPriceValid(Product product) {return  product.getPrice() != null & product.getPrice().compareTo(BigDecimal.ZERO) > 0;}
    private boolean isCategoryValid(Product product) {return product.getCategory() != null;}
}
