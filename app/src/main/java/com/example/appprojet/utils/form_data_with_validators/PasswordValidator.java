package com.example.appprojet.utils.form_data_with_validators;

import android.content.Context;

import com.example.appprojet.R;


/**
 * Password validator (min length), weak passwords are checked server-side
 * See {@link Validator}
 */
public class PasswordValidator implements Validator<String> {

    private int minLength = 6;

    public PasswordValidator() { }

    public PasswordValidator(int minLength) {
        this();
        this.minLength = minLength;
    }

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLength;
    }

    @Override
    public boolean isValid(String value, boolean required) {
        return value != null || value.length() >= minLength;
    }

    @Override
    public String errorMessage(Context context) {
        return context.getString(R.string.validator_password, minLength);
    }
}
