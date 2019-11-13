package com.example.appprojet.utils.form_data_with_validators;


import android.content.Context;

/**
 * Text input field data
 *
 * It holds the input data, and have a Validator to check if the data inside the text field is
 * 'valid' or note.
 */
public class FormData<T> {

    private FormType type;
    private T value;
    private Validator<T> validator;


    public FormData(Validator<T> validator, FormType type, T initialValue) {
        this.validator  = validator;
        this.type = type == null ? FormType.TEXT : type;
        this.value = initialValue;
    }

    public FormData(Validator<T> validator, FormType type) {
        this(validator, type, null);
    }

    public FormData(Validator<T> validator) {
        this(validator, FormType.TEXT);
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

    public FormType getType() {
        return type;
    }

    public void setValidator(Validator<T> validator) {
        this.validator = validator;
    }

    public enum FormType {TEXT, DATE, LOCATION}
}
