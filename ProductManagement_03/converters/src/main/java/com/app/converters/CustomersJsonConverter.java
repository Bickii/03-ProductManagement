package com.app.converters;

import com.app.model.Customer;

import java.util.List;

public class CustomersJsonConverter extends JsonConverter<List<Customer>> {
    public CustomersJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
