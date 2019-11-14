package com.progmobile.meetchup.utils.form_data_with_validators;

import android.content.Context;

import com.progmobile.meetchup.R;

/**
 * Basic validator for non-empty value
 * See {@link Validator}
 */
public class BasicValidator implements Validator {

    @Override
    public boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    @Override
    public String errorMessage(Context context) {
        return context.getString(R.string.validator_basic);
    }
}
