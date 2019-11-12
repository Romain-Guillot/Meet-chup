package com.example.appprojet.ui.authentication.sign_in_form;

import android.app.Application;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.form_data_with_validators.BasicValidator;
import com.example.appprojet.utils.form_data_with_validators.FormData;


/**
 * ViewModel that extends the FormViewModel to handle the sign in form data and communicate with the
 * authentication repository.
 * See the FormViewModel documentation for more details.
 *
 * There are two form data :
 *  - the email form data
 *  - the password form data
 *
 * Note: the data validation is performed by Validator instances that are directly given to the form
 * data when creating them.
 */
public class SignInViewModel extends FormViewModel {

    private final IAuthenticationRepository authenticationRepository;
    final FormData emailLive = new FormData(new BasicValidator());
    final FormData passwordLive = new FormData(new BasicValidator());

    public SignInViewModel(Application application) {
        super(application);
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
    }

    @Override
    protected void submitForm() {
        if (validate()) {
            isLoadingLive.setValue(true);
            String email = emailLive.getValue();
            String password = passwordLive.getValue();
            authenticationRepository.classicSignIn(email, password, new SubmitCallback<>());
        }
    }

    @Override
    protected boolean validate() {
        return emailLive.isValid() && passwordLive.isValid();
    }
}
