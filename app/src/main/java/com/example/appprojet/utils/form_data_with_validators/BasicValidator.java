package com.example.appprojet.utils.form_data_with_validators;

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
