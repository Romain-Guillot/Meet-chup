package com.example.appprojet.utils.form_data_with_validators;

import android.content.Context;

import com.example.appprojet.R;


/**
 * Basic validator for username (min and max length)
 * See {@link Validator}
 *
 * Deprecated, instead use a basic validator {@link BasicValidator} (min length and max length are
 * defined by the repos, it's cleaner)
 */
@Deprecated
public class NameValidator implements Validator<String> {

    private final int minLength = 6;
    private final int maxLength = 20;

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLength && value.length() <= maxLength;
    }

    @Override
    public boolean isValid(String value, boolean required) {
        return false;  // deprecated, not implemented
    }

    @Override
    public String errorMessage(Context context) {
        return context.getString(R.string.validator_username, minLength, maxLength);
    }
}
