package com.example.appprojet.utils.form_data_with_validators;


import android.content.Context;

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
