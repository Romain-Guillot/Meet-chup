package com.example.appprojet.utils;

import android.content.Context;

import com.example.appprojet.R;


public class CallbackException extends Exception {

    public Type type;

    public CallbackException(Type type) {
        this.type = type;
    }
    public CallbackException() {
        type = Type.UNKNOWN;
    }

    public String getErrorMessage(Context context) {
        switch (type) {
            case UNKNOWN:
            default:
                return context.getString(R.string.unknown_error);
        }
    }

    enum Type {
        NO_INTERNET,

        AUTH_INVALID_CREDENTIAL,
        AUTH_UNKNOWN,

        UNKNOWN,
    }
}
