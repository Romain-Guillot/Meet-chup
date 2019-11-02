package com.example.appprojet.ui.authentication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.R;
import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.Callback;


/**
 * ViewModel associated with a [FormFragment] to handle the following form flags :
 *  - isLoading when we wait a response from the repository
 *  - errorLive when an error occurred
 *
 * When the FormViewModel is create, the authentication repository instance is set.
 *
 * When extending this class, submitForm and validate methods have to be implements.
 *
 * TODO: Change error message based on the FirebaseException return by the repo
 */
public abstract class FormViewModel extends AndroidViewModel {

    /** Authentication repository instance*/
    protected final IAuthenticationRepository authenticationRepository;

    /** Loading flag (when the form is being processed) */
    protected final MutableLiveData<Boolean> isLoadingLive = new MutableLiveData<>(false);

    /** Error flag (when an error occurred) */
    protected final MutableLiveData<String> errorLive = new MutableLiveData<>();

    /** callback to give to the repository when submitting the form */
    protected final Callback<User> submitCallback = new Callback<User>() {
        @Override
        public void onSucceed(User result) {
            errorLive.setValue(null);
        }

        @Override
        public void onFail(Exception e) {
            isLoadingLive.setValue(false);
            errorLive.setValue(getApplication().getString(R.string.auth_error_form));
        }
    };


    protected FormViewModel(Application application) {
        super(application);
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
    }

    /** Retrieve form data and send info to the authentication repository */
    protected abstract void submitForm();

    /** check if the data in the form is valid.  */
    protected abstract boolean validate();

}
