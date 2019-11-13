package com.example.appprojet.utils.form_data_with_validators;

import android.content.Context;

import com.example.appprojet.utils.Location;

public class LocationValidator implements Validator<Location> {
    @Override
    public boolean isValid(Location value) {
        return false;
    }

    @Override
    public boolean isValid(Location value, boolean required) {
        return true;
    }

    @Override
    public String errorMessage(Context context) {
        return null;
    }
}
