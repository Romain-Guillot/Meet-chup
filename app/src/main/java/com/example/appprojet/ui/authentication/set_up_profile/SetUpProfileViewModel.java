package com.example.appprojet.ui.authentication.set_up_profile;


import androidx.lifecycle.MutableLiveData;

import com.example.appprojet.models.User;
import com.example.appprojet.ui.authentication.FormViewModel;
import com.example.appprojet.utils.custom_live_data.FormMutableLiveData;
import com.example.appprojet.utils.custom_live_data.NameValidator;
import com.example.appprojet.utils.Callback;

public class SetUpProfileViewModel extends FormViewModel {


    final FormMutableLiveData nameLive = new FormMutableLiveData(new NameValidator());
    final MutableLiveData<Boolean> isFinish = new MutableLiveData<>(false);

    public SetUpProfileViewModel() {
        super();
        User user = authenticationRepository.getUser();
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
                    isFinish.setValue(true);
                }

                @Override
                public void onFail(Exception e) {
                    isLoadingLive.setValue(false);
                    errorLive.setValue(e.toString());
                }
            });
        }
    }


    @Override
    protected boolean validate() {
        return nameLive.isValid();
    }
}
