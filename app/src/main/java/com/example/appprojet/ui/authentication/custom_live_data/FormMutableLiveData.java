package com.example.appprojet.ui.authentication.custom_live_data;

import androidx.lifecycle.MutableLiveData;

public class FormMutableLiveData extends MutableLiveData<String> {

    Validator validator;

    public FormMutableLiveData(Validator validator) {
        this.validator = validator;
    }


    public boolean isValid() {
        return validator.isValid(getValue());
    }

    public String getError() {
        return validator.errorMessage();
    }
}
