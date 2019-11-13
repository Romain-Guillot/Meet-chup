package com.progmobile.meetchup.ui.authentication.sign_up_form;

import android.app.Application;

import com.progmobile.meetchup.repositories.FirebaseAuthenticationRepository;
import com.progmobile.meetchup.repositories.IAuthenticationRepository;
import com.progmobile.meetchup.utils.FormViewModel;
import com.progmobile.meetchup.utils.form_data_with_validators.EmailValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.FormData;
import com.progmobile.meetchup.utils.form_data_with_validators.PasswordConfirmationValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.PasswordValidator;


/**
 * ViewModel that extends the FormViewModel to handle the sign up form data and communicate with the
 * authentication repository.
 * See the FormViewModel documentation for more details.
 *
 * There are three form data :
 *  - the email form data
 *  - the password form data
 *  - the password confirmation data
 *
 * Note: the data validation is performed by Validator instances that are directly given to the form
 * data when creating them.
 */
public class SignUpViewModel extends FormViewModel {

    private final IAuthenticationRepository authenticationRepository;
    final FormData emailLive = new FormData(new EmailValidator());
    final FormData passwordLive = new FormData(new PasswordValidator());
    final FormData passwordConfirmLive = new FormData(new PasswordConfirmationValidator(passwordLive));


    public SignUpViewModel(Application application) {
        super(application);
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
    }

    @Override
    protected void submitForm() {
        if (validate()) {
            isLoadingLive.setValue(true);
            String email = emailLive.getValue();
            String password = passwordLive.getValue();
            authenticationRepository.classicSignUp(email, password, new SubmitCallback<>());
        }
    }

    @Override
    protected boolean validate() {
        return emailLive.isValid() && passwordLive.isValid() && passwordConfirmLive.isValid();
    }
}
