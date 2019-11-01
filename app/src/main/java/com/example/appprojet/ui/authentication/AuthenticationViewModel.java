package com.example.appprojet.ui.authentication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AuthenticationViewModel extends ViewModel {

    enum FormType {
        SIGNIN, SIGNUP, SETUP
    }

    MutableLiveData<FormType> currentFormTypeLive = new MutableLiveData<>(FormType.SIGNIN);
    MutableLiveData<Boolean> moveToHomePage = new MutableLiveData<>(false);


    void switchSignInSignUpForm() {
        AuthenticationViewModel.FormType currentFormType = currentFormTypeLive.getValue();
        if (currentFormType == null || currentFormType.equals(AuthenticationViewModel.FormType.SETUP)) {
            // unexpected
            currentFormTypeLive.setValue(AuthenticationViewModel.FormType.SIGNIN);
        } else {
            switch (currentFormType) {
                case SIGNUP:
                    currentFormTypeLive.setValue(AuthenticationViewModel.FormType.SIGNIN);
                    break;
                case SIGNIN:
                    currentFormTypeLive.setValue(AuthenticationViewModel.FormType.SIGNUP);
                    break;
            }
        }
    }

    void finish() {
        moveToHomePage.setValue(true);
    }

}
