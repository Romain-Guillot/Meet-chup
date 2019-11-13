package com.progmobile.meetchup.utils;


/**
 * Callback to notify the sender when an action fails or succeeds
 * <p>
 * Callback are typically used to wait responses of repositories that perform asynchronous
 * tasks.
 * <p>
 * See {@link CallbackException} for more details in case of failure return
 */
public interface Callback<T> {

    void onSucceed(T result);

    void onFail(CallbackException exception);
}
