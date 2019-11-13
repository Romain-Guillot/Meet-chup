package com.progmobile.meetchup.utils.form_data_with_validators;

import android.content.Context;

import com.progmobile.meetchup.R;


/**
 * Basic validator for username (min and max length)
 * See {@link Validator}
 */
public class NameValidator implements Validator {

    private final int minLength = 6;
    private final int maxLength = 20;

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLength && value.length() <= maxLength;
    }

    @Override
    public String errorMessage(Context context) {
        return context.getString(R.string.validator_username, minLength, maxLength);
    }
}
