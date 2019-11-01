package com.example.appprojet.ui.authentication.sign_auth;

import com.example.appprojet.models.User;
import com.example.appprojet.ui.authentication.FormViewModel;
import com.example.appprojet.utils.custom_live_data.BasicValidator;
import com.example.appprojet.utils.custom_live_data.FormMutableLiveData;
import com.example.appprojet.utils.Callback;


public class SignInViewModel extends FormViewModel {

    FormMutableLiveData emailLive = new FormMutableLiveData(new BasicValidator());
    FormMutableLiveData passwordLive = new FormMutableLiveData(new BasicValidator());


     protected void submitForm() {
        if (validate()) {
            isLoadingLive.setValue(true);
            String email =emailLive.getValue();
            String password = passwordLive.getValue();
            authenticationRepository.classicSignIn(email, password, new Callback<User>() {
                @Override
                public void onSucceed(User result) {
                    errorLive.setValue(null);
                }

                @Override
                public void onFail(Exception e) {
                    isLoadingLive.setValue(false);
                    errorLive.setValue(e.toString());
                }
            });
        }
    }

    protected boolean validate() {
        return emailLive.isValid() && passwordLive.isValid();
    }
}
