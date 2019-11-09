package com.example.appprojet.ui.event_view.invitation;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.Event;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.SingleEvent;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.InvitationKeyValidator;

public class InvitationKeyViewModel extends FormViewModel {

    private IEventsDataRepository eventsDataRepository;
    private String eventID;

    final MutableLiveData<Boolean> keyEnabledLive = new MutableLiveData<>(false);
    final MutableLiveData<SingleEvent<Boolean>> updateKeyField = new MutableLiveData<>();
    final FormData eventKeyLive = new FormData(new InvitationKeyValidator());


    public InvitationKeyViewModel(Application application) {
        super(application);
        eventsDataRepository = FirestoreEventsDataRepository.getInstance();
    }


    void init(String eventID) {
        this.eventID = eventID;
        eventsDataRepository.getEvent(eventID, new Callback<Event>() {
            public void onSucceed(Event result) {
                keyEnabledLive.setValue(result.getInvitationKey() != null);
                eventKeyLive.setValue(result.getInvitationKey());
                updateKeyField.setValue(new SingleEvent<>(true));
            }

            public void onFail(CallbackException exception) {

            }
        });
    }

    /**
     * Retrieve form data and send info to the authentication repository
     */
    @Override
    protected void submitForm() {
        if (validate()) {
            isLoadingLive.setValue(true);
            String key = eventKeyLive.getValue();
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
        return eventKeyLive.isValid();
    }

    void removeInvitationKey() {
        eventsDataRepository.removeEventInvitationKey(eventID, new Callback<Void>() {
            public void onSucceed(Void result) {
                keyEnabledLive.setValue(false);
                eventKeyLive.setValue(null);
                updateKeyField.setValue(new SingleEvent<>(true));
            }
            public void onFail(CallbackException exception) { keyEnabledLive.setValue(true); }
        });
    }

}
