package com.progmobile.meetchup.ui.event_creation;

import android.app.Activity;
import android.app.Application;


import androidx.lifecycle.MutableLiveData;

import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.form_data_with_validators.DateValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.LocationValidator;
import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.repositories.IEventsDataRepository;
import com.progmobile.meetchup.utils.FormViewModel;
import com.progmobile.meetchup.utils.Location;
import com.progmobile.meetchup.utils.SingleEvent;
import com.progmobile.meetchup.utils.form_data_with_validators.BasicValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.FormData;

import java.util.Date;


/**
 *
 */
public class EventCreationViewModel extends FormViewModel {

    private IEventsDataRepository eventRepo;

    MutableLiveData<String> eventCreated = new MutableLiveData<>();
    String eventID = null;
    Event event = null;

    FormData<String> titleField = new FormData<>(new BasicValidator(IEventsDataRepository.EVENT_TITLE_MIN_LENGTH, IEventsDataRepository.EVENT_TITLE_MAX_LENGTH));
    FormData<String> descriptionField = new FormData<>(new BasicValidator(), false);
    FormData<Date> beginDate = new FormData<>(new DateValidator(), false);
    FormData<Date> endDate = new FormData<>(new DateValidator(), false);
    FormData<Location> location = new FormData<>(new LocationValidator(), false);


    public EventCreationViewModel(Application application) {
        super(application);
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }

    protected void setExistingEvent(Activity observer, String eventID) {
        this.eventID = eventID;
        eventRepo.getEvent(observer, eventID, new Callback<Event>() {
            public void onSucceed(Event result) {
                titleField.setValue(result.getTitle());
                descriptionField.setValue(result.getDescription());
                beginDate.setValue(result.getDateBegin());
                endDate.setValue(result.getDateEnd());
                location.setValue(result.getLocation());
                updateKeyEvent.setValue(new SingleEvent<>(true));
                event = result;
            }

            public void onFail(CallbackException exception) { }
        });
    }

    /**
     * Retrieve form data and send info to the repository
     */
    @Override
    protected void submitForm() {
        if (validate() && eventCreated.getValue() == null && (isLoadingLive.getValue() == null || !isLoadingLive.getValue())) {
            isLoadingLive.setValue(true);

            Callback<String> callback = new Callback<String>() {
                public void onSucceed(String result) {
                    eventCreated.setValue(result);
                    new SubmitCallback<>().onSucceed(result);
                }
                public void onFail(CallbackException exception) {
                    new SubmitCallback<>().onFail(exception);
                }
            };

            Date now = new Date();
            Event event = new Event(null, titleField.getValue(), descriptionField.getValue(),
                    null, beginDate.getValue(), endDate.getValue(), now,
                    location.getValue(), null);

            if (eventID != null) {
                eventRepo.updateEvent(eventID, event, callback);
            } else {
                eventRepo.createEvent(event, callback);
            }
        } else {
            errorLive.setValue(new SingleEvent<>("Invalid form"));
        }
    }

    /**
     * check if the data in the form is valid.
     */
    @Override
    protected boolean validate() {
        return titleField.isValid() && descriptionField.isValid() && beginDate.isValid() && endDate.isValid() && location.isValid();
    }
}
