package com.example.appprojet.ui.invitation;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.appprojet.models.Event;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.SingleEvent;
import com.example.appprojet.utils.form_data_with_validators.BasicValidator;
import com.example.appprojet.utils.form_data_with_validators.FormData;


/**
 * FormViewModel {@link FormViewModel} that handled the loading, error and success management
 * Here, we implement the submit button behavior (ask the event repo for deleting or updating
 * the invitation key event)
 */
public class InvitationKeyViewModel extends FormViewModel {

    private IEventsDataRepository eventsDataRepository;
    private String eventID;

    final MutableLiveData<Boolean> keyEnabledLive = new MutableLiveData<>(false);
    final FormData<String> eventKeyFieldLive = new FormData<>(new BasicValidator(IEventsDataRepository.INVITATION_KEY_MIN_LENGTH, IEventsDataRepository.INVITATION_KEY_MAX_LENGTH));

    final MutableLiveData<SingleEvent<Boolean>> updateKeyEvent = new MutableLiveData<>();


    public InvitationKeyViewModel(Application application) {
        super(application);
        eventsDataRepository = FirestoreEventsDataRepository.getInstance();
    }

    /**
     * Listen for the event, each event modification will be notify through the callback
     * according the documentation of the event repo {@link IEventsDataRepository}
     */
    void init(Activity activity, String eventID) {
        this.eventID = eventID;
        eventsDataRepository.getEvent(activity, eventID, new Callback<Event>() {
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

    /**
     * Request for removing the invitation key
     */
    void removeInvitationKey() {
        eventsDataRepository.deleteEventInvitationKey(eventID, new Callback<Void>() {
            public void onSucceed(Void result) {
                keyEnabledLive.setValue(false);
                eventKeyFieldLive.setValue(null);
                updateKeyEvent.setValue(new SingleEvent<>(true));
            }
            public void onFail(CallbackException exception) { keyEnabledLive.setValue(true); }
        });
    }

}
