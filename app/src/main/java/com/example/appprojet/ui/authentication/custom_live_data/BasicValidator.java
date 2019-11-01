package com.example.appprojet.ui.authentication.custom_live_data;

public class BasicValidator implements Validator {

    @Override
    public boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    @Override
    public String errorMessage() {
        return "Empty value not authorized";
    }
}
