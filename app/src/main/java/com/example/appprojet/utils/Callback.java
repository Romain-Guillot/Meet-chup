package com.example.appprojet.utils;


/**
 * Callback to notify the sender when an action is fails or succeeds
 */
public interface Callback<T> {

    public void onSucceed(T result);

    public void onFail(CallbackException exception);
}
