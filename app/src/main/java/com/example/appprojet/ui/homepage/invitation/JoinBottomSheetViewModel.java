package com.example.appprojet.ui.homepage.invitation;

import android.app.Application;


import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.SingleEvent;
import com.example.appprojet.utils.form_data_with_validators.FormData;


public class JoinBottomSheetViewModel extends FormViewModel {

    IEventsDataRepository eventsDataRepository;
    String keyFormData = null;

    public JoinBottomSheetViewModel(Application application) {
        super(application);
        eventsDataRepository = FirestoreEventsDataRepository.getInstance();
    }

    /**
     * Retrieve form data and send info to the authentication repository
     */
    @Override
    protected void submitForm() {
        if (validate()) {
            isLoadingLive.setValue(true);
            eventsDataRepository.joinEvent(keyFormData, new Callback<String>() {
                @Override
                public void onSucceed(String result) {
                    isLoadingLive.setValue(false);
                    successLive.setValue(new SingleEvent<>(true));
                }

                @Override
                public void onFail(CallbackException exception) {
                    isLoadingLive.setValue(false);
                    errorLive.setValue(new SingleEvent<>(exception.getErrorMessage(getApplication())));
                }
            });
        } else {
            errorLive.setValue(new SingleEvent<>("Invalid key"));
        }
    }

    /**
     * check if the data in the form is valid.
     */
    @Override
    protected boolean validate() {
        return keyFormData != null && keyFormData.length() >= 1;
    }
}
