package com.example.appprojet.ui.event_creation;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.Event;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.example.appprojet.utils.FormViewModel;
import com.example.appprojet.utils.form_data_with_validators.BasicValidator;
import com.example.appprojet.utils.form_data_with_validators.FormData;

public class EventCreationViewModel extends FormViewModel {

    private IEventsDataRepository eventRepo;

    FormData titleField = new FormData(new BasicValidator());
    FormData descriptionField = new FormData(new BasicValidator());
    FormData beginDate = new FormData(null);
    FormData endDate = new FormData(null);
    FormData location = new FormData(null);


    protected EventCreationViewModel(Application application) {
        super(application);
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }

    /**
     * Retrieve form data and send info to the repository
     */
    @Override
    protected void submitForm() {

        if (validate()) {
            Event event = new Event(null, titleField.getValue(), descriptionField.getValue(), beginDate.getValue(), endDate.getValue(), null, location.getValue(), null);
            eventRepo.createEvent(null, new SubmitCallback<>());

        }
    }

    /**
     * check if the data in the form is valid.
     */
    @Override
    protected boolean validate() {
        return false;
    }
}
