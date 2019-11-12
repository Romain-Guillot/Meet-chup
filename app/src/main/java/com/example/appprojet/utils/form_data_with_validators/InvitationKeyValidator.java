package com.example.appprojet.utils.form_data_with_validators;


import android.content.Context;

import com.example.appprojet.R;

/**
 * Deprecated, instead use a basic validator {@link BasicValidator} (min length and max length are
 * defined by the repos, it's cleaner)
 */
@Deprecated
public class InvitationKeyValidator implements Validator<String> {

    public static int minLength = 8;

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLength;
    }

    @Override
    public boolean isValid(String value, boolean required) {
        return false; // deprecated, not implemented
    }

    @Override
    public String errorMessage(Context context) {
        return context.getString(R.string.validator_invitation_key, minLength);
    }
}
