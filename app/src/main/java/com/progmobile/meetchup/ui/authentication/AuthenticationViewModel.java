package com.progmobile.meetchup.ui.authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progmobile.meetchup.models.User;
import com.progmobile.meetchup.repositories.FirebaseAuthenticationRepository;
import com.progmobile.meetchup.repositories.IAuthenticationRepository;


/**
 * Handle the authentication activity form state lifecycle. It is composed of two LiveData :
 * - currentTypeFormLive : the current form state (sign in, sign up, set up)
 * - isFinish : a flag to indicate that the authentication process is done
 * <p>
 * The recommended way to handle the form state is to use the two followings method :
 * - switchSignInSignUpForm to switch between the sign in form and the sign up form
 * - finish to set the finish flag to true
 * <p>
 * In its default configuration, the current form state is the sign in form, and of course the
 * finish flag is set to false.
 * <p>
 * We add a listener to the user auth state when the instance is created, and we delete this
 * listener when the onCleared method is called.
 */
public class AuthenticationViewModel extends ViewModel {

    /**
     * current form state : sign in, sign up or set up profile
     */
    final MutableLiveData<FormType> currentFormTypeLive = new MutableLiveData<>(FormType.SIGNIN);
    /**
     * flag to indicate if the authentication process is done or not
     */
    final MutableLiveData<Boolean> isFinish = new MutableLiveData<>(false);
    /**
     * Current app authentication repository
     */
    private final IAuthenticationRepository authenticationRepository;
    /**
     * the user live data retrieve from the authentication repo
     */
    LiveData<User> userLive;


    /**
     * NEVER CREATE AN INSTANCE BY YOURSELF
     */
    public AuthenticationViewModel() {
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
        userLive = authenticationRepository.getObservableUser();
    }

    /**
     * Switch the current state form between the sign in and the sign up form
     */
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
     * Set the finish flag to true to ends the process (typically destroyed the activity)
     */
    public void finish() {
        isFinish.setValue(true);
    }

    /**
     * Form type used to handle current form state
     */
    enum FormType {
        SIGNIN, SIGNUP, SETUP
    }
}
