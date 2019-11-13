package com.progmobile.meetchup.ui.event_creation;

import android.app.Application;


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

public class EventCreationViewModel extends FormViewModel {

    private IEventsDataRepository eventRepo;

    FormData<String> titleField = new FormData<>(new BasicValidator());
    FormData<String> descriptionField = new FormData<>(new BasicValidator());
    FormData<Date> beginDate = new FormData<>(new DateValidator(), FormData.FormType.DATE);
    FormData<Date> endDate = new FormData<>(new DateValidator(), FormData.FormType.DATE);
    FormData<Location> location = new FormData<>(new LocationValidator(), FormData.FormType.LOCATION);


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
            Event event = new Event(null, titleField.getValue(), descriptionField.getValue(),
                    null, beginDate.getValue(), endDate.getValue(), null,
                    location.getValue(), null);
            eventRepo.createEvent(event, new SubmitCallback<>());
        } else {
            errorLive.setValue(new SingleEvent<>("Invalid form"));
        }
    }

    /**
     * check if the data in the form is valid.
     */
    @Override
    protected boolean validate() {
        return titleField.isValid() && descriptionField.isValid(false) && beginDate.isValid(false) && endDate.isValid(false) && location.isValid(false);
    }
}
