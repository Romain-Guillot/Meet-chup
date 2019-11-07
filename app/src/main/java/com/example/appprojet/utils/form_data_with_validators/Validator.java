package com.example.appprojet.utils.form_data_with_validators;

public interface Validator {

    boolean isValid(String value);

    String errorMessage();
}
