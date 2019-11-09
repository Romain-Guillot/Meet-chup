package com.example.appprojet.utils.form_data_with_validators;


import android.content.Context;

import com.example.appprojet.R;

public class InvitationKeyValidator implements Validator {

    public static int minLength = 8;

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= minLength;
    }

    @Override
    public String errorMessage(Context context) {
        return context.getString(R.string.validator_invitation_key, minLength);
    }
}
