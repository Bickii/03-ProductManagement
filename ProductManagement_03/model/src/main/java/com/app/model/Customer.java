package com.app.model;

import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Customer {

        private String name;
        private String surname;
        private int age;
        private String email;
}
