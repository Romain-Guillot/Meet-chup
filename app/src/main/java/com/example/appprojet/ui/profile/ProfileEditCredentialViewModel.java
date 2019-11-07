package com.example.appprojet.ui.profile;

import android.app.Application;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.form_data_with_validators.BasicValidator;
import com.example.appprojet.utils.form_data_with_validators.EmailValidator;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.PasswordConfirmationValidator;
import com.example.appprojet.utils.form_data_with_validators.PasswordValidator;


public class ProfileEditCredentialViewModel extends FormViewModel {

    IAuthenticationRepository authRepo;

    FormData passwordFormData = new FormData(new BasicValidator());
    FormData emailFormData = new FormData(new EmailValidator());
    FormData newPasswordFormData = new FormData(new PasswordValidator());
    FormData newPasswordFormDataConfirmation = new FormData(new PasswordConfirmationValidator(newPasswordFormData));

    public ProfileEditCredentialViewModel(Application application) {
        super(application);
        authRepo = FirebaseAuthenticationRepository.getInstance();
        User user = authRepo.getCurrentUser();
        if (user != null)
            emailFormData.setValue(user.getEmail());
    }

    /**
     * Retrieve form data and send info to the authentication repository
     */
    @Override
    protected void submitForm() {

    }

    /**
     * check if the data in the form is valid.
     */
    @Override
    protected boolean validate() {
        return false;
    }
}
