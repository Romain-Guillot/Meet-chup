package com.progmobile.meetchup.utils;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


/**
 * ViewModel associated with a [FormFragment] to handle the following form flags and event :
 * - isLoading when we wait a response from the repository
 * - errorLive event when an error occurred
 * - success event when a success message is available
 * <p>
 * When the FormViewModel is create, the authentication repository instance is set.
 * <p>
 * When extending this class, submitForm and validate methods have to be implements.
 * <p>
 * The callback give to the repository set the correcting flags and event LiveData, if you want
 * to add more operations when the callback succeeds of fails, do like that :
 * <p>
 * methodToRepo(arg1, arg2, new Callback<...> {
 * onSucceed(...) {
 * new SubmitCallback().onSucceeds()
 * }
 * onFails(...) {
 * new SubmitCallback().onFails()
 * }
 * });
 * <p>
 * It's definitively not ideal but it's like call super method to update the super class (here the
 * FormViewModel) but as is asynchronous is not that easy.
 */
public abstract class FormViewModel extends AndroidViewModel {

    /**
     * Loading flag (when the form is being processed)
     */
    public final MutableLiveData<Boolean> isLoadingLive = new MutableLiveData<>(false);

    /**
     * Error flag (when an error occurred)
     */
    public final MutableLiveData<SingleEvent<String>> errorLive = new MutableLiveData<>();

    /**
     * Event when submit succes
     */
    public final MutableLiveData<SingleEvent<Boolean>> successLive = new MutableLiveData<>();

    /**
     * Event to force field content update
     */
    public final MutableLiveData<SingleEvent<Boolean>> updateKeyEvent = new MutableLiveData<>();


    protected FormViewModel(Application application) {
        super(application);
    }

    /**
     * Retrieve form data and send info to the repository
     */
    protected abstract void submitForm();

    /**
     * check if the data in the form is valid.
     */
    protected abstract boolean validate();

    /**
     * callback to give to the repository when submitting the form
     */
    public class SubmitCallback<T> implements Callback<T> {
        @Override
        public void onSucceed(T result) {
            isLoadingLive.setValue(false);
            successLive.setValue(new SingleEvent<>(true));
        }

        @Override
        public void onFail(CallbackException e) {
            isLoadingLive.setValue(false);
            errorLive.setValue(new SingleEvent<>(e.getErrorMessage(getApplication().getApplicationContext())));
        }
    }
}
