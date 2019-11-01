package com.example.appprojet.ui.authentication.custom_live_data;

public interface Validator {
    boolean isValid(String value);

    String errorMessage();
}
