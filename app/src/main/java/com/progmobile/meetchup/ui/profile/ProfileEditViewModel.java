package com.progmobile.meetchup.ui.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.User;
import com.progmobile.meetchup.repositories.FirebaseAuthenticationRepository;
import com.progmobile.meetchup.repositories.IAuthenticationRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.SingleEvent;
import com.progmobile.meetchup.utils.form_data_with_validators.BasicValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.EmailValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.FormData;
import com.progmobile.meetchup.utils.form_data_with_validators.PasswordConfirmationValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.PasswordValidator;



/**
 * View model to handle process of ProfileViewFragment to update profile.
 * <p>
 * There are one FormData for each field and on is loading state Live data for each form, there are
 * 3 forms :
 * - email
 * - username
 * - password
 * <p>
 * The fragment ask the repo to perform actions and update Live Data according the responses.
 */
public class ProfileEditViewModel extends AndroidViewModel {

    final MutableLiveData<SingleEvent<String>> errorLive = new MutableLiveData<>();
    final MutableLiveData<SingleEvent<String>> successLive = new MutableLiveData<>(null);

    final FormData<String> emailFormData = new FormData<>(new EmailValidator());
    final MutableLiveData<Boolean> emailFormIsLoading = new MutableLiveData<>(false);

    final FormData<String> newPasswordFormData = new FormData<>(new PasswordValidator());
    final FormData<String> newPasswordConfirmFormData = new FormData<>(new PasswordConfirmationValidator(newPasswordFormData));
    final MutableLiveData<Boolean> newPasswordFormIsLoading = new MutableLiveData<>(false);

    final FormData<String> usernameFormData = new FormData<>(new BasicValidator(IAuthenticationRepository.NAME_MIN_LENGTH, IAuthenticationRepository.NAME_MAX_LENGTH));

    final MutableLiveData<Boolean> usernameFormIsLoading = new MutableLiveData<>(false);
    final MutableLiveData<Boolean> isDeleted = new MutableLiveData<>();

    private final IAuthenticationRepository authRepo;


    public ProfileEditViewModel(Application application) {
        super(application);
        authRepo = FirebaseAuthenticationRepository.getInstance();
        User user = authRepo.getCurrentUser();
        if (user != null) {
            emailFormData.setValue(user.getEmail());
            usernameFormData.setValue(user.getName());
        }
    }



    void submitEmailForm() {
        if (emailFormData.isValid()) {
            emailFormIsLoading.setValue(true);
            authRepo.updateEmail(emailFormData.getValue(), new UpdateCallBack(emailFormIsLoading));
        }
    }

    void submitNewPasswordForm() {
        if (newPasswordFormData.isValid() && newPasswordConfirmFormData.isValid()) {
            newPasswordFormIsLoading.setValue(true);
            authRepo.updatePassword(newPasswordConfirmFormData.getValue(), new UpdateCallBack(newPasswordFormIsLoading));
        }
    }

    void submitUsernameForm() {
        if (usernameFormData.isValid()) {
            usernameFormIsLoading.setValue(true);
            authRepo.updateName(usernameFormData.getValue(), new UpdateCallBack(usernameFormIsLoading));
        }
    }

    void deleteAccount() {
        authRepo.deleteAccount(new Callback<Void>() {
            @Override
            public void onSucceed(Void result) {
                isDeleted.setValue(true);
            }

            @Override
            public void onFail(CallbackException exception) {
                errorLive.setValue(new SingleEvent<>(exception.getErrorMessage(getApplication())));
            }
        });
    }

    private class UpdateCallBack implements Callback<User> {

        final MutableLiveData<Boolean> loadingLiveData;

        UpdateCallBack(MutableLiveData<Boolean> loadingLiveData) {
            this.loadingLiveData = loadingLiveData;
        }

        @Override
        public void onSucceed(User result) {
            loadingLiveData.setValue(false);
            successLive.setValue(new SingleEvent<>(getApplication().getString(R.string.profile_updated_success)));
        }

        @Override
        public void onFail(CallbackException exception) {
            loadingLiveData.setValue(false);
            errorLive.setValue(new SingleEvent<>(exception.getErrorMessage(getApplication())));
        }
    }
}
