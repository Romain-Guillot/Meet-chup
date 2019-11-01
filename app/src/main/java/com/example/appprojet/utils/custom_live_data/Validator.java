package com.example.appprojet.utils.custom_live_data;

public interface Validator {
    boolean isValid(String value);

    String errorMessage();
}
