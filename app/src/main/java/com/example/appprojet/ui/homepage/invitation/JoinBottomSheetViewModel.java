package com.example.appprojet.ui.homepage.invitation;

import android.app.Application;


import com.example.appprojet.R;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.SingleEvent;


/**
 * {@link FormViewModel}
 * Form view model to handle the join invitation form.
 * The unique field is the invitation key, we simply call the repository when the user submit the
 * form and the view model live data are updated according to the repo response
 * (isLoadingLice, successLive, errorLive)
 */
public class JoinBottomSheetViewModel extends FormViewModel {

    private final IEventsDataRepository eventsDataRepository;
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
                    errorLive.setValue(new SingleEvent<>(getApplication().getString(R.string.error_invitation_key_notfound)));
                }
            });
        } else {
            errorLive.setValue(new SingleEvent<>(getApplication().getString(R.string.error_invitation_key_notfound)));
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
