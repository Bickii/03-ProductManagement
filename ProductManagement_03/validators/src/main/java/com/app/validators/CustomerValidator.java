package com.app.validators;

import com.app.model.Customer;
import org.apache.commons.validator.routines.EmailValidator;


import java.util.Map;

public class CustomerValidator extends AbstractValidator<Customer> {

    public final Map<String, String> validate(Customer customer) {
        errors.clear();
        if (customer == null) {
            errors.put("customer", "customer object is null");
        }
        if (!isNameValid(customer)) {
            errors.put("customer name", "customer name is not valid" + customer.getName());
        }
        if (!isSurnameValid(customer)) {
            errors.put("customer surname", "customer surname is not valid" + customer.getSurname());
        }
        if (!isBirthDateValid(customer)) {
            errors.put("customer birthdate", "customer birthdate is not valid" + customer.getAge());
        }
        if (!isEmailValid(customer)) {
            errors.put("customer email", "customer email is not valid" + customer.getEmail());
        }
        return errors;

    }

    private boolean isNameValid(Customer customer) {
        return customer.getName() != null & customer.getName().matches("^[A-Z\\s]+$");
    }

    private boolean isSurnameValid(Customer customer) {
        return customer.getSurname() != null & customer.getSurname().matches("^[A-Z\\s]+$");
    }

    private boolean isBirthDateValid(Customer customer) {
        return customer.getAge() > 18;
    }

    private boolean isEmailValid(Customer customer) {
        return customer.getEmail() != null & EmailValidator.getInstance().isValid(customer.getEmail());
    }
    //dependency walidacja emaila
}
