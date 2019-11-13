package com.progmobile.meetchup.utils.form_data_with_validators;


import android.content.Context;

import com.progmobile.meetchup.R;

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
