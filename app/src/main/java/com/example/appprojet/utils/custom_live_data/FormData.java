package com.example.appprojet.utils.custom_live_data;


public class FormData {

    private String value;
    private Validator validator;


    public FormData(Validator validator, String initialValue) {
        this.validator  = validator;
        this.value = initialValue;
    }

    public FormData(Validator validator) {
        this(validator, null);
    }


    public boolean isValid() {
        return validator.isValid(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getError() {
        return validator.errorMessage();
    }
}
