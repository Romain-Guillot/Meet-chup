package com.example.appprojet.utils.form_data_with_validators;

public class NameValidator implements Validator {

    private int minLenght = 6;
    private int maxLenght = 20;

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLenght && value.length() <= maxLenght;
    }

    @Override
    public String errorMessage() {
        return "Your name must be " + minLenght + " to " + maxLenght + " characters long";
    }
}
