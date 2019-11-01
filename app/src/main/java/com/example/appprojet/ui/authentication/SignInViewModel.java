package com.example.appprojet.ui.authentication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.ui.authentication.custom_live_data.EmailValidator;
import com.example.appprojet.ui.authentication.custom_live_data.FormMutableLiveData;
import com.example.appprojet.ui.authentication.custom_live_data.PasswordValidator;
import com.example.appprojet.utils.Callback;

public class SignInViewModel extends ViewModel {

    IAuthenticationRepository authenticationRepository;

    FormMutableLiveData emailLive = new FormMutableLiveData(new EmailValidator());
    FormMutableLiveData passwordLive = new FormMutableLiveData(new PasswordValidator());

    MutableLiveData<Boolean> isLoadingLive = new MutableLiveData<>(false);
    MutableLiveData<String> errorLive = new MutableLiveData<>();

    MutableLiveData<Boolean> isLoggedLive = new MutableLiveData<>(false);


    public SignInViewModel() {
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
    }


    void submitForm() {
        if (validate()) {
            isLoadingLive.setValue(true);
            String email =emailLive.getValue();
            String password = passwordLive.getValue();
            authenticationRepository.classicSignIn(email, password, new Callback<User>() {
                @Override
                public void onSucceed(User result) {
                    isLoadingLive.setValue(false);
                    isLoggedLive.setValue(true);
                    errorLive.setValue(null);
                }

                @Override
                public void onFail(Exception e) {
                    isLoadingLive.setValue(false);
                    isLoggedLive.setValue(false);
                    errorLive.setValue(e.toString());
                }
            });
        }
    }

    private boolean validate() {
        return emailLive.isValid() && passwordLive.isValid();
    }

}
