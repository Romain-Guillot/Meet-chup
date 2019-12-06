package com.progmobile.meetchup.ui.event_view;

import android.app.Activity;
import android.app.Application;
import android.location.Address;
import android.location.Geocoder;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.ListenerRegistration;
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

    String eventID = null;
    MutableLiveData<Event> eventMetaData = new MutableLiveData<>();
    MutableLiveData<List<Post>> eventPosts = new MutableLiveData<>();
    MutableLiveData<Boolean> requestQuitingIsLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> quitingRequestDone = new MutableLiveData<>();
    private IEventsDataRepository eventsRepo;


    public EventViewViewModel(Application application) {
        super(application);
        eventsRepo = FirestoreEventsDataRepository.getInstance();
    }


    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void initEventMetaData(Activity activity) {
        eventsRepo.getEvent(activity, eventID, new Callback<Event>() {
            public void onSucceed(Event result) {
                eventMetaData.setValue(result);
            }

            public void onFail(CallbackException e) {

            }
        });
    }


    public ListenerRegistration loadPosts() {
        return eventsRepo.allPosts(eventID, new Callback<List<Post>>() {
            public void onSucceed(List<Post> result) {
                eventPosts.setValue(result);
            }

            public void onFail(CallbackException exception) {

            }
        });
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
