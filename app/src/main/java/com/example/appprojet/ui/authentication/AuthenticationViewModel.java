package com.example.appprojet.ui.authentication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.utils.Callback;


public class AuthenticationViewModel extends ViewModel {

    enum FormType {
        SIGNIN, SIGNUP, SETUP
    }

    MutableLiveData<FormType> currentFormTypeLive = new MutableLiveData<>(FormType.SIGNIN);
    MutableLiveData<Boolean> moveToHomePage = new MutableLiveData<>(false);


    public AuthenticationViewModel() {
        registerUserStateListener();
    }


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


    private void registerUserStateListener() {
        FirebaseAuthenticationRepository.getInstance().addAuthStateListener(new Callback<User>() {
            @Override
            public void onSucceed(User result) {
                Log.e(">>>>>>>", "" + result.isFirstLogIn());
                if (result != null) {
                    if (result.isFirstLogIn()) currentFormTypeLive.setValue(FormType.SETUP);
                    else moveToHomePage.setValue(true);
                }
            }
            @Override public void onFail(Exception e) { }
        });
    }


}
