package com.example.appprojet.ui.authentication;

import androidx.lifecycle.ViewModel;

public class AuthenticationViewModel extends ViewModel {

    enum FormType {
        SIGNIN, SIGNUP, SETUP
    }

    FormType currentFormType = FormType.SIGNIN;

}
