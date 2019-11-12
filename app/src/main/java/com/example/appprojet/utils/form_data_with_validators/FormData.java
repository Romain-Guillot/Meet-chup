package com.example.appprojet.utils.form_data_with_validators;


import android.content.Context;

/**
 * Text input field data
 *
 * It holds the input data, and have a Validator to check if the data inside the text field is
 * 'valid' or note.
 */
public class FormData<T> {

    private T value;
    private final Validator<T> validator;


    public FormData(Validator<T> validator, T initialValue) {
        this.validator  = validator;
        this.value = initialValue;
    }

    public FormData(Validator<T> validator) {
        this(validator, null);
    }

    /** Return true if the data is valid (good formatting, etc.) */
    public boolean isValid() {
        return validator.isValid(value);
    }
    public boolean isValid(boolean required) {
        return validator.isValid(value, required);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getError(Context context) {
        return validator.errorMessage(context);
    }
}
