package com.progmobile.meetchup.ui.authentication.set_up_profile;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.progmobile.meetchup.models.User;
import com.progmobile.meetchup.repositories.FirebaseAuthenticationRepository;
import com.progmobile.meetchup.repositories.IAuthenticationRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.FormViewModel;
import com.progmobile.meetchup.utils.form_data_with_validators.FormData;
import com.progmobile.meetchup.utils.form_data_with_validators.NameValidator;


/**
 * ViewModel that extends the FormViewModel to handle the set up profile form data and communicate
 * with the authentication repository.
 * See the FormViewModel documentation for more details.
 * <p>
 * There are only one form data :
 * - the name form data
 * <p>
 * Note: the data validation is performed by Validator instances that are directly given to the form
 * data when creating them.
 * <p>
 * Note : this is the last step of the authentication process. When the set up processes is finished
 * this flag is set to true. There may be a more elegant solution to be found
 */
public class SetUpProfileViewModel extends FormViewModel {

    final FormData nameLive = new FormData(new NameValidator());
    final MutableLiveData<Boolean> isFinish = new MutableLiveData<>(false);
    private final IAuthenticationRepository authenticationRepository;


    public SetUpProfileViewModel(Application application) {
        super(application);
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
        User user = authenticationRepository.getCurrentUser();
        if (user != null)
            nameLive.setValue(user.getName());
    }

    @Override
    protected void submitForm() {
        if (validate()) {
            isLoadingLive.setValue(true);
            String name = nameLive.getValue();
            authenticationRepository.updateName(name, new Callback<User>() {
                @Override
                public void onSucceed(User result) {
                    new SubmitCallback<User>().onSucceed(result);
                    isFinish.setValue(true);
                }

                @Override
                public void onFail(CallbackException e) {
                    new SubmitCallback().onFail(e);
                }
            });
        }
    }


    @Override
    protected boolean validate() {
        return nameLive.isValid();
    }
}
