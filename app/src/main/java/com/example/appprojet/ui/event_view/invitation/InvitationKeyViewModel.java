package com.example.appprojet.ui.event_view.invitation;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.appprojet.models.Event;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.SingleEvent;
import com.example.appprojet.utils.SnackbarFactory;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.InvitationKeyValidator;
import com.google.android.material.snackbar.Snackbar;


/**
 *
 */
public class InvitationKeyViewModel extends FormViewModel {

    private IEventsDataRepository eventsDataRepository;
    private String eventID;

    final MutableLiveData<Boolean> keyEnabledLive = new MutableLiveData<>(false);
    final FormData eventKeyFieldLive = new FormData(new InvitationKeyValidator());

    final MutableLiveData<SingleEvent<Boolean>> updateKeyEvent = new MutableLiveData<>();


    public InvitationKeyViewModel(Application application) {
        super(application);
        eventsDataRepository = FirestoreEventsDataRepository.getInstance();
    }


    void init(String eventID) {
        this.eventID = eventID;
        eventsDataRepository.getEvent(eventID, new Callback<Event>() {
            public void onSucceed(Event result) {
                keyEnabledLive.setValue(result.getInvitationKey() != null);
                eventKeyFieldLive.setValue(result.getInvitationKey());
                updateKeyEvent.setValue(new SingleEvent<>(true));
            }

            public void onFail(CallbackException exception) {
                errorLive.setValue(new SingleEvent<>(exception.getErrorMessage(getApplication())));
            }
        });
    }

    /**
     * Retrieve form data and send info to the authentication repository
     */
    @Override
    protected void submitForm() {
        String key = eventKeyFieldLive.getValue();
        if (validate()) {
            isLoadingLive.setValue(true);
            eventsDataRepository.updateEventInvitationKey(eventID, key, new Callback<String>() {
                public void onSucceed(String result) {
                    new SubmitCallback<>().onSucceed(result);
                    keyEnabledLive.setValue(true);
                }
                public void onFail(CallbackException e) { new SubmitCallback<>().onFail(e); }
            });
        }
    }

    /**
     * check if the data in the form is valid.
     */
    @Override
    protected boolean validate() {
        return eventKeyFieldLive.isValid();
    }

    void removeInvitationKey() {
        eventsDataRepository.removeEventInvitationKey(eventID, new Callback<Void>() {
            public void onSucceed(Void result) {
                keyEnabledLive.setValue(false);
                eventKeyFieldLive.setValue(null);
                updateKeyEvent.setValue(new SingleEvent<>(true));
            }
            public void onFail(CallbackException exception) { keyEnabledLive.setValue(true); }
        });
    }

}
