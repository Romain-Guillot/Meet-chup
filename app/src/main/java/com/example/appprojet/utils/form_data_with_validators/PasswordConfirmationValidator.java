package com.example.appprojet.utils.form_data_with_validators;


public class PasswordConfirmationValidator implements Validator {

    private FormData originalPassword;


    public PasswordConfirmationValidator(FormData originalPassword) {
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
