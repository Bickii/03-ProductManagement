package com.app.model;

import com.app.enums.Category;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Product {
    private String name;
    private BigDecimal price;
    private Category category;

}
