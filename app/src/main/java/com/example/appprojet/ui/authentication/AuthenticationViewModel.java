package com.example.appprojet.ui.authentication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.utils.Callback;


/**
 * View model intended to handle the authentication activity form state lifecycle. It is composed of
 * two LiveData :
 *  - currentTypeFormLive : the current form state (sign in, sign up, set up)
 *  - isFinish : a flag to indicate that the authentication process is done
 *
 * The recommended way to handle the form state is to use the two followings method :
 *  - switchSignInSignUpForm to switch between the sign in form and the sign up form
 *  - finish to set the finish flag to true
 *
 *  In its default configuration, the current form state is the sign in form, and of course the
 *  finish flag is set to false.
 */
public class AuthenticationViewModel extends ViewModel {

    /** current form state : sign in, sign up or set up profile */
    final MutableLiveData<FormType> currentFormTypeLive = new MutableLiveData<>(FormType.SIGNIN);

    /** flag to indicate if the authentication process is done or not */
    final MutableLiveData<Boolean> isFinish = new MutableLiveData<>(false);


    /** NEVER CREATE AN INSTANCE BY YOURSELF */
    public AuthenticationViewModel() {
        registerUserStateListener();
    }

    /** Switch the current state form between the sign in and the sign up form*/
    void switchSignInSignUpForm() {
        FormType currentFormType = currentFormTypeLive.getValue();

        // Note : the user is not expected to be able to change between the login and registration
        // form at the set up state.
        if (currentFormType != null && !currentFormType.equals(FormType.SETUP)) {
            switch (currentFormType) {
                case SIGNUP:
                    currentFormTypeLive.setValue(FormType.SIGNIN);
                    break;
                case SIGNIN:
                    currentFormTypeLive.setValue(FormType.SIGNUP);
                    break;
            }
        }
    }

    /**
     * Listen the current user authentication state. When a non null user is returned through the
     * callback :
     *  - if it's a new user (first connexion) -> we update the current form state to the set up form
     *  - else -> we call the finish method to end the process
     */
    private void registerUserStateListener() {
        FirebaseAuthenticationRepository.getInstance().addAuthStateListener(new Callback<User>() {
            @Override public void onFail(Exception e) { }
            @Override
            public void onSucceed(User result) {
                if (result != null) {
                    if (result.isFirstLogIn()) currentFormTypeLive.setValue(FormType.SETUP);
                    else finish();
                }
            }
        });
    }

    /** Set the finish flag to true to ends the process (typically destroyed the activity) */
    public void finish() {
        isFinish.setValue(true);
    }

    /** Form type used to handle current form state */
    enum FormType {
        SIGNIN, SIGNUP, SETUP
    }

}
