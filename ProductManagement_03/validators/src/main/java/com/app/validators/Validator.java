package com.app.validators;

import java.util.Map;

public interface Validator<T> {
    Map<String, String> validate(T element);
    boolean hasErrors();
}
