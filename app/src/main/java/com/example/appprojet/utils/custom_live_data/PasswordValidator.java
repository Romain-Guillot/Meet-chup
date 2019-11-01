package com.example.appprojet.utils.custom_live_data;

public class PasswordValidator implements Validator {

    private int minLenght = 8;

    public PasswordValidator() {

    }

    public PasswordValidator(int minLenght) {
        this();
        this.minLenght = minLenght;
    }

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLenght;
    }

    @Override
    public String errorMessage() {
        return "Invlaid pass";
    }
}
