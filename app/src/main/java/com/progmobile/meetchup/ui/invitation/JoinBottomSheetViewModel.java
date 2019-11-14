package com.progmobile.meetchup.ui.invitation;

import android.app.Application;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.repositories.IEventsDataRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.FormViewModel;
import com.progmobile.meetchup.utils.SingleEvent;


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
