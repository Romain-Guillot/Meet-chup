package com.progmobile.meetchup.ui.invitation;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.repositories.IEventsDataRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.FormViewModel;
import com.progmobile.meetchup.utils.SingleEvent;
import com.progmobile.meetchup.utils.form_data_with_validators.FormData;
import com.progmobile.meetchup.utils.form_data_with_validators.InvitationKeyValidator;


/**
 * FormViewModel {@link FormViewModel} that handled the loading, error and success management
 * Here, we implement the submit button behavior (ask the event repo for deleting or updating
 * the invitation key event)
 */
public class InvitationKeyViewModel extends FormViewModel {

    final MutableLiveData<Boolean> keyEnabledLive = new MutableLiveData<>(false);
    final FormData eventKeyFieldLive = new FormData(new InvitationKeyValidator());
    final MutableLiveData<SingleEvent<Boolean>> updateKeyEvent = new MutableLiveData<>();
    private IEventsDataRepository eventsDataRepository;
    private String eventID;


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

                public void onFail(CallbackException e) {
                    new SubmitCallback<>().onFail(e);
                }
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

            public void onFail(CallbackException exception) {
                keyEnabledLive.setValue(true);
            }
        });
    }

}
