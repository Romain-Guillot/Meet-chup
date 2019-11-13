package com.example.appprojet.ui.event_creation;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.Event;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.Location;
import com.example.appprojet.utils.SingleEvent;
import com.example.appprojet.utils.form_data_with_validators.BasicValidator;
import com.example.appprojet.utils.form_data_with_validators.DateValidator;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.LocationValidator;

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
