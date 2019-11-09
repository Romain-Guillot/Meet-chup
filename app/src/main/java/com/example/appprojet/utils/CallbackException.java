package com.example.appprojet.utils;

import android.content.Context;
import android.util.Log;

import com.example.appprojet.R;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


/**
 * Exception return by Callback in case of failure
 *
 * You can create your exceptions from the both constructors or with the static method
 * fromFirebaseException() that create an CallbackException instance from a FirebaseException
 * (Note that with the default constructor the default type is Type.UNKNOWN, it is therefore not
 * recommended to use this constructor as it gives no information on the type of error.)
 *
 * Callback exceptions have a type, and you can get the error message with the current application
 * context with the method getErrorMessage()
 */
public class CallbackException extends Exception {

    private final Type type;

    public CallbackException(Type type) {
        this.type = type;
    }

    public CallbackException() {
        type = Type.UNKNOWN;
    }

    /** Get the exception message, a toString with a context*/
    public String getErrorMessage(Context context) {
        switch (type) {
            case AUTH_WEAK_PASSWORD:
                return context.getString(R.string.error_weak_password);
            case RE_AUTH_REQUIRED:
                return context.getString(R.string.error_re_auth_required);
            case AUTH_EMAIL_COLLISION:
                return context.getString(R.string.error_mail_collision);
            case AUTH_INVALID_CREDENTIAL:
                return context.getString(R.string.error_invalid_credentials);
            case NETWORK_ERROR:
                return context.getString(R.string.error_network);
            case NO_LOGGED:
                return context.getString(R.string.error_nologged);

            case UNKNOWN:
            case AUTH_UNKNOWN:
            default:
                return context.getString(R.string.error_unknown);
        }
    }


    /** Create a CallbackException from a FirebaseException */
    public static CallbackException fromFirebaseException(Exception e) {
        if (e != null) Log.e(">>>>>>>>", e.toString());

        if (e == null)
            return new CallbackException(Type.UNKNOWN);
        if (e instanceof FirebaseNetworkException)
            return new CallbackException(Type.NETWORK_ERROR);
        if (e instanceof FirebaseAuthWeakPasswordException)
            return new CallbackException(Type.AUTH_WEAK_PASSWORD);
        if (e instanceof FirebaseAuthInvalidCredentialsException)
            return new CallbackException(Type.AUTH_INVALID_CREDENTIAL);
        if (e instanceof FirebaseAuthInvalidUserException || e instanceof FirebaseAuthRecentLoginRequiredException)
            return new CallbackException(Type.RE_AUTH_REQUIRED);
        if (e instanceof FirebaseAuthUserCollisionException)
            return new CallbackException(Type.AUTH_EMAIL_COLLISION);
        if (e instanceof FirebaseAuthException)
            return new CallbackException(Type.AUTH_UNKNOWN);

        return new CallbackException(Type.UNKNOWN);
    }


    /** Types of errors */
    public enum Type {
        /** When there is a problem with the internet connection to perform an action */
        NETWORK_ERROR,

        /** When a user try to sign-in with a wrong pair of email / password*/
        AUTH_INVALID_CREDENTIAL,

        /** When the user choose a password too weak */
        AUTH_WEAK_PASSWORD,

        /** When the user try to create an account with an existing user email */
        AUTH_EMAIL_COLLISION,

        /** Other authentication exceptions */
        AUTH_UNKNOWN,

        /** When a re-authentication is required to perform an action*/
        RE_AUTH_REQUIRED,

        /** When the user try to perform an action that required to be logged but he is not */
        NO_LOGGED,

        /** If no errors above match */
        UNKNOWN,
    }
}
