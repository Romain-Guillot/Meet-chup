package com.example.appprojet.ui.event_view;

import android.telecom.Call;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.Event;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;


public class EventViewViewModel extends ViewModel {

    private IEventsDataRepository eventsRepo;

    protected MutableLiveData<Event> eventLiveData = new MutableLiveData<>();


    public EventViewViewModel() {
        eventsRepo = FirestoreEventsDataRepository.getInstance();
    }


    public void initEvent(String eventId) {
        eventsRepo.getEvent(null, eventId, new Callback<Event>() {
            @Override
            public void onSucceed(Event result) {
                Log.d(">>>>", result.getTitle());
                eventLiveData.setValue(result);
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }


    public void loadPosts() {
        Log.d(">>>>", "load posts");
        eventsRepo.loadEventPosts(null, eventLiveData.getValue(), new Callback<Event>() {
            @Override
            public void onSucceed(Event result) {

            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }


}
