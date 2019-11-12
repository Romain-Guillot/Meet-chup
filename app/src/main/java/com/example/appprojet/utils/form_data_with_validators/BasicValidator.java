package com.example.appprojet.utils.form_data_with_validators;

import android.content.Context;

import com.example.appprojet.R;

/**
 * Basic validator for non-empty value
 * See {@link Validator}
 */
public class BasicValidator implements Validator<String> {

    private int minLength = 0;
    private int maxLength = Integer.MAX_VALUE;

    public BasicValidator() {}

    public BasicValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLength && value.length() <= maxLength;
    }

    @Override
    public boolean isValid(String value, boolean required) {
        return value == null || (value.length() >= minLength && value.length() <= maxLength);
    }

    @Override
    public String errorMessage(Context context) {
        return context.getString(R.string.validator_basic, minLength, maxLength);
    }
}
