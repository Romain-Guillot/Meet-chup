package com.progmobile.meetchup.utils.form_data_with_validators;


import android.content.Context;

import com.progmobile.meetchup.utils.form_data_with_validators.Validator;

import java.util.Date;


public class DateValidator implements Validator<Date> {

    @Override
    public boolean isValid(Date value) {
        return false;
    }

    @Override
    public boolean isValid(Date value, boolean required) {
        return true;
    }

    @Override
    public String errorMessage(Context context) {
        return null;
    }
}
