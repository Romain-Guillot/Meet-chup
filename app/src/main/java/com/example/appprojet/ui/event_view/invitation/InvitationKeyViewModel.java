package com.example.appprojet.ui.event_view.invitation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.Event;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.InvitationKeyValidator;

public class InvitationKeyViewModel extends ViewModel {

    private IEventsDataRepository eventsDataRepository;

    MutableLiveData<Boolean> keyEnabledLive = new MutableLiveData<>(false);
    FormData eventKeyLive = new FormData(new InvitationKeyValidator());


    public InvitationKeyViewModel() {
        eventsDataRepository = FirestoreEventsDataRepository.getInstance();
    }

    public void init(String eventKey) {
        eventsDataRepository.getEvent(eventKey, new Callback<Event>() {
            @Override
            public void onSucceed(Event result) {
                eventKeyLive.setValue(result.getInvitationKey());
                keyEnabledLive.setValue(result.getInvitationKey() != null);
            }

            @Override
            public void onFail(CallbackException exception) {

            }
        });
    }

    public void updateKey() {
        if (eventKeyLive.isValid()) {
            String key = eventKeyLive.getValue();

        }
    }
}
