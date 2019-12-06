package com.progmobile.meetchup.ui.homepage;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.ListenerRegistration;
import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.repositories.IEventsDataRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;

import java.util.List;

public class HomepageViewModel extends ViewModel {

    private IEventsDataRepository eventRepo;

    MutableLiveData<List> userEventsLive = new MutableLiveData<>();

    public HomepageViewModel() {
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }

    ListenerRegistration init() {
        return eventRepo.allEvents(new Callback<List<Event>>() {
            public void onSucceed(List<Event> result) {
                userEventsLive.setValue(result);
            }

            public void onFail(CallbackException exception) {

            }
        });
    }

}
