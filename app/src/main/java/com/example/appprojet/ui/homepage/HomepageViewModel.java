package com.example.appprojet.ui.homepage;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.Event;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;

import java.util.List;

public class HomepageViewModel extends ViewModel {

    private IEventsDataRepository eventRepo;

    MutableLiveData<List> userEventsLive = new MutableLiveData<>();

    public HomepageViewModel() {
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }

    void init(Activity activity) {
        eventRepo.allEvents(activity, new Callback<List<Event>>() {
            public void onSucceed(List<Event> result) {
                userEventsLive.setValue(result);
            }

            public void onFail(CallbackException exception) {

            }
        });
    }

}
