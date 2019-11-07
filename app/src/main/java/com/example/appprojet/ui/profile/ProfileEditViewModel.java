package com.example.appprojet.ui.profile;

import android.app.Application;
import android.telecom.Call;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.R;
import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.form_data_with_validators.BasicValidator;
import com.example.appprojet.utils.form_data_with_validators.EmailValidator;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.NameValidator;
import com.example.appprojet.utils.form_data_with_validators.PasswordConfirmationValidator;
import com.example.appprojet.utils.form_data_with_validators.PasswordValidator;


public class ProfileEditViewModel extends AndroidViewModel {

    IAuthenticationRepository authRepo;

    MutableLiveData<String> errorLive = new MutableLiveData<>(null);
    MutableLiveData<String> successLive = new MutableLiveData<>(null);

    FormData emailFormData = new FormData(new EmailValidator());
    MutableLiveData<Boolean> emailFormIsLoading = new MutableLiveData<>(false);

    FormData newPasswordFormData = new FormData(new PasswordValidator());
    FormData newPasswordConfirmFormData = new FormData(new PasswordConfirmationValidator(newPasswordFormData));
    MutableLiveData<Boolean> newPasswordFormIsLoading = new MutableLiveData<>(false);

    FormData usernameFormData = new FormData(new NameValidator());
    MutableLiveData<Boolean> usernameFormIsLoading = new MutableLiveData<>(false);


    public ProfileEditViewModel(Application application) {
        super(application);
        authRepo = FirebaseAuthenticationRepository.getInstance();
        User user = authRepo.getCurrentUser();
        if (user != null) {
            emailFormData.setValue(user.getEmail());
            usernameFormData.setValue(user.getName());
        }
    }

    private void resetInfoLiveData() {
        errorLive.setValue(null);
        successLive.setValue(null);
    }

    void submitEmailForm() {
        resetInfoLiveData();
        if (emailFormData.isValid()) {
            emailFormIsLoading.setValue(true);
            authRepo.updateEmail(emailFormData.getValue(), new UpdateCallBack(emailFormIsLoading));
        }
    }

    void submitNewPasswordForm() {
        resetInfoLiveData();
        if (newPasswordFormData.isValid() && newPasswordConfirmFormData.isValid()) {
            newPasswordFormIsLoading.setValue(true);
            authRepo.updatePassword(newPasswordConfirmFormData.getValue(), new UpdateCallBack(newPasswordFormIsLoading));
        }
    }

    void submitUsernameForm() {
        resetInfoLiveData();
        if (usernameFormData.isValid()) {
            usernameFormIsLoading.setValue(true);
            authRepo.updateName(usernameFormData.getValue(), new UpdateCallBack(usernameFormIsLoading));
        }
    }

    private class UpdateCallBack implements Callback<User> {

        MutableLiveData<Boolean> loadingLiveData;

        UpdateCallBack(MutableLiveData<Boolean> loadingLiveData) {
            this.loadingLiveData = loadingLiveData;
        }

        @Override
        public void onSucceed(User result) {
            loadingLiveData.setValue(false);
            successLive.setValue(getApplication().getString(R.string.profile_updated_success));
        }

        @Override
        public void onFail(CallbackException exception) {
            loadingLiveData.setValue(false);
            errorLive.setValue(exception.getErrorMessage(getApplication()));
        }
    }
}
