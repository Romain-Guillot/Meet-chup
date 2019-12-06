package com.progmobile.meetchup.ui.event_view;

import android.app.Activity;
import android.app.Application;
import android.location.Address;
import android.location.Geocoder;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.models.User;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.repositories.IEventsDataRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.DurationUtils;
import com.progmobile.meetchup.utils.Location;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 */
public class EventViewViewModel extends AndroidViewModel {

    MutableLiveData<Event> eventMetaData = new MutableLiveData<>();
    MutableLiveData<List<Post>> eventPosts = new MutableLiveData<>();
    MutableLiveData<Boolean> requestQuitingIsLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> quitingRequestDone = new MutableLiveData<>();
    private IEventsDataRepository eventsRepo;


    public EventViewViewModel(Application application) {
        super(application);
        eventsRepo = FirestoreEventsDataRepository.getInstance();
    }


    public void initEventMetaData(Activity activity, String eventId) {
        eventsRepo.getEvent(activity, eventId, new Callback<Event>() {
            public void onSucceed(Event result) {
                eventMetaData.setValue(result);
            }

            public void onFail(CallbackException e) {

            }
        });
    }


    public void loadPosts() {

    }




    void requestQuitEvent() {
        Event event = eventMetaData.getValue();
        if (event == null) return ;
        requestQuitingIsLoading.setValue(true);
        eventsRepo.quitEvent(event.getId(), new Callback<Void>() {
            public void onSucceed(Void result) {
                requestQuitingIsLoading.setValue(false);
                quitingRequestDone.setValue(true);
            }

            public void onFail(CallbackException exception) {
                requestQuitingIsLoading.setValue(false);
                quitingRequestDone.setValue(false);
            }
        });
    }
}
