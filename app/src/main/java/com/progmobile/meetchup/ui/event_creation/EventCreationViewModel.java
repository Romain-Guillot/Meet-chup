package com.progmobile.meetchup.ui.event_creation;

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

    FormData<String> titleField = new FormData<>(new BasicValidator(IEventsDataRepository.EVENT_TITLE_MIN_LENGTH, IEventsDataRepository.EVENT_TITLE_MAX_LENGTH));
    FormData<String> descriptionField = new FormData<>(new BasicValidator(), false);
    FormData<Date> beginDate = new FormData<>(new DateValidator(), false);
    FormData<Date> endDate = new FormData<>(new DateValidator(), false);
    FormData<Location> location = new FormData<>(new LocationValidator(), false);


    public EventCreationViewModel(Application application) {
        super(application);
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }

    /**
     * Retrieve form data and send info to the repository
     */
    @Override
    protected void submitForm() {
        if (validate()) {
            Date now = new Date();
            Event event = new Event(null, titleField.getValue(), descriptionField.getValue(),
                    null, beginDate.getValue(), endDate.getValue(), now,
                    location.getValue(), null);
            eventRepo.createEvent(event, new Callback<String>() {
                @Override
                public void onSucceed(String result) {
                    new SubmitCallback<>().onSucceed(result);
                    eventCreated.setValue(result);
                }

                @Override
                public void onFail(CallbackException exception) {
                    new SubmitCallback<>().onFail(exception);
                }
            });
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
