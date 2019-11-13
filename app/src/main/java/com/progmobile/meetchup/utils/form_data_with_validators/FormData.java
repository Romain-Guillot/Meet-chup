package com.progmobile.meetchup.utils.form_data_with_validators;


import android.content.Context;

/**
 * Text input field data
 *
 * It holds the input data, and have a Validator to check if the data inside the text field is
 * 'valid' or note.
 */
public class FormData {

    private String value;
    private final Validator validator;


    public FormData(Validator validator, String initialValue) {
        this.validator  = validator;
        this.value = initialValue;
    }

    public FormData(Validator validator) {
        this(validator, null);
    }

    /** Return true if the data is valid (good formatting, etc.) */
    public boolean isValid() {
        return validator.isValid(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getError(Context context) {
        return validator.errorMessage(context);
    }
}
