package com.example.appprojet.ui.authentication.sign_up_form;

import com.example.appprojet.models.User;
import com.example.appprojet.ui.authentication.FormViewModel;
import com.example.appprojet.utils.custom_live_data.EmailValidator;
import com.example.appprojet.utils.custom_live_data.FormMutableLiveData;
import com.example.appprojet.utils.custom_live_data.PasswordValidator;
import com.example.appprojet.utils.Callback;


/**
 *
 */
public class SignUpViewModel extends FormViewModel {

    final FormMutableLiveData emailLive = new FormMutableLiveData(new EmailValidator());
    final FormMutableLiveData passwordLive = new FormMutableLiveData(new PasswordValidator());
    final FormMutableLiveData passwordConfirmLive = new FormMutableLiveData(null);

    @Override
    protected void submitForm() {
        if (validate()) {
            String email = emailLive.getValue();
            String password = passwordLive.getValue();
            authenticationRepository.classicSignUp(email, password, new Callback<User>() {
                @Override
                public void onSucceed(User result) {

                }

                @Override
                public void onFail(Exception e) {

                }
            });
        }
    }

    @Override
    protected boolean validate() {
        return emailLive.isValid() && passwordLive.isValid() && passwordConfirmLive.isValid();
    }
}
