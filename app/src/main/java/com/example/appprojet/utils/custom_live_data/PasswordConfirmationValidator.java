package com.example.appprojet.utils.custom_live_data;

import androidx.lifecycle.LiveData;

public class PasswordConfirmationValidator implements Validator {

    private LiveData<String> originalPassword;

    public PasswordConfirmationValidator(LiveData<String> originalPassword) {
        this.originalPassword = originalPassword;
    }

    @Override
    public boolean isValid(String value) {
        return value != null && value.equals(originalPassword.getValue());
    }

    @Override
    public String errorMessage() {
        return "Password doesn't match";
    }
}
