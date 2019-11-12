package com.example.appprojet.utils.form_data_with_validators;

import android.content.Context;

/**
 * Abstract class to create string validator. A Validator is typically used with a FormData,
 * see '{@link FormData} for more details.
 *
 * A validator have two methods, one to validate the a value with the validator rules, one to get
 * the validator error message.
 *
 * NOTE : IT IS ONLY CLIENT VERIFICATION, THAT DOESN'T REMOVE SERVER-SIDE VERIFICATIONS
 */
public interface Validator<T> {

    boolean isValid(T value);

    boolean isValid(T value, boolean required);

    String errorMessage(Context context);
}
