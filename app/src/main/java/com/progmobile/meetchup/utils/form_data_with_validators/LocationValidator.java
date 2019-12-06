package com.progmobile.meetchup.utils.form_data_with_validators;

import android.content.Context;

import com.progmobile.meetchup.utils.Location;
import com.progmobile.meetchup.utils.form_data_with_validators.Validator;


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
