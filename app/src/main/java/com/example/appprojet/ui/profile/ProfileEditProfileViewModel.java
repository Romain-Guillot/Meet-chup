package com.example.appprojet.ui.profile;

import android.app.Application;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.NameValidator;


public class ProfileEditProfileViewModel extends FormViewModel {

    IAuthenticationRepository authRepo;

    FormData usernameFormData = new FormData(new NameValidator());


    public ProfileEditProfileViewModel(Application application) {
        super(application);
        authRepo = FirebaseAuthenticationRepository.getInstance();
        User user = authRepo.getCurrentUser();
        if (user != null)
            usernameFormData.setValue(user.getName());
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
