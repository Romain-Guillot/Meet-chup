package com.example.appprojet.ui.authentication.custom_live_data;

public class NameValidator implements Validator {
    @Override
    public boolean isValid(String value) {
        return true;
    }

    @Override
    public String errorMessage() {
        return "";
    }
}
