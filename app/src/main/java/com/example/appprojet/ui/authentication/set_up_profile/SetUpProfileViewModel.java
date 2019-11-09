package com.example.appprojet.ui.authentication.set_up_profile;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.NameValidator;
import com.example.appprojet.utils.Callback;


/**
 * ViewModel that extends the FormViewModel to handle the set up profile form data and communicate
 * with the authentication repository.
 * See the FormViewModel documentation for more details.
 *
 * There are only one form data :
 *  - the name form data
 *
 * Note: the data validation is performed by Validator instances that are directly given to the form
 * data when creating them.
 *
 * Note : this is the last step of the authentication process. When the set up processes is finished
 * this flag is set to true. There may be a more elegant solution to be found
 */
public class SetUpProfileViewModel extends FormViewModel {

    private final IAuthenticationRepository authenticationRepository;
    final FormData nameLive = new FormData(new NameValidator());
    final MutableLiveData<Boolean> isFinish = new MutableLiveData<>(false);


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
