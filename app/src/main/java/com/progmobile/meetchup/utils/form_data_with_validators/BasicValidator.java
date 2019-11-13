package com.progmobile.meetchup.utils.form_data_with_validators;

import android.content.Context;

import com.progmobile.meetchup.R;

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
        if (maxLength != Integer.MAX_VALUE)
            return context.getString(R.string.validator_basic, minLength, maxLength);
        if (minLength == 0)
            return context.getString(R.string.validator_basic_not_empty);
        return context.getString(R.string.validator_basic_no_max, minLength);
    }
}
