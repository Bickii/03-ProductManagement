package com.app.converters;
import com.app.model.Order;

import java.util.List;

public class OrdersJsonConverter extends JsonConverter<List<Order>> {
    public OrdersJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
