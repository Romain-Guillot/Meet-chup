package com.example.appprojet.ui.authentication.set_up_profile;


import android.util.Log;

import com.example.appprojet.models.User;
import com.example.appprojet.ui.authentication.FormViewModel;
import com.example.appprojet.ui.authentication.custom_live_data.FormMutableLiveData;
import com.example.appprojet.ui.authentication.custom_live_data.NameValidator;
import com.example.appprojet.utils.Callback;

public class SetUpProfileViewModel extends FormViewModel {


    FormMutableLiveData nameLive = new FormMutableLiveData(new NameValidator());


    public SetUpProfileViewModel() {
        super();
        User user = authenticationRepository.getUser();
        if (user != null)
            nameLive.setValue(user.getName());
    }

    @Override
    protected void submitForm() {
        if (validate()) {
            Log.d(">>>>>>>>>>>", "1");
            String name = nameLive.getValue();
            authenticationRepository.updateName(name, new Callback<User>() {
                @Override
                public void onSucceed(User result) {
                    Log.e(">>>>>>>", "OKKKK");
                }

                @Override
                public void onFail(Exception e) {
                    Log.e(">>>>>>>", "NONNN");
                    errorLive.setValue(e.toString());
                }
            });
        }
        Log.d(">>>>>>>>>>>", "2");


    }

    @Override
    protected boolean validate() {
        return nameLive.isValid();
    }
}
