package com.progmobile.meetchup.utils.form_data_with_validators;


import android.content.Context;

/**
 * Text input field data
 * <p>
 * It holds the input data, and have a Validator to check if the data inside the text field is
 * 'valid' or note.
 */
public class FormData<T> {

    private T value;
    private Validator<T> validator;
    private boolean required;


    public FormData(Validator<T> validator, T initialValue, boolean required) {
        this.validator  = validator;
        this.value = initialValue;
        this.required = required;
    }

    public FormData(Validator<T> validator, boolean required) {
        this(validator, null, required);
    }

    public FormData(Validator<T> validator) {
        this(validator, null, true);
    }


    /**
     * Return true if the data is valid (good formatting, etc.)
     */
    public boolean isValid() {
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

    public void setValidator(Validator<T> validator) {
        this.validator = validator;
    }

}
