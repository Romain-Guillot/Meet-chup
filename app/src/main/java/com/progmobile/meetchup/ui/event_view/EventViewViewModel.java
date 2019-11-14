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

    MutableLiveData<String> eventTitleLive = new MutableLiveData<>();
    MutableLiveData<String> eventBeginDateLive = new MutableLiveData<>();
    MutableLiveData<String> eventDurationLive = new MutableLiveData<>();
    MutableLiveData<String> eventLocationLive = new MutableLiveData<>();
    MutableLiveData<String> eventDescriptionLive = new MutableLiveData<>();
    MutableLiveData<List<User>> eventParticipantsList = new MutableLiveData<>();
    MutableLiveData<List<Post>> eventPosts = new MutableLiveData<>();
    MutableLiveData<Boolean> requestQuitingIsLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> quitingRequestDone = new MutableLiveData<>();
    private IEventsDataRepository eventsRepo;
    private Event event = null;
    private DateFormat dateFormat = DateFormat.getDateInstance();


    public EventViewViewModel(Application application) {
        super(application);
        eventsRepo = FirestoreEventsDataRepository.getInstance();
    }


    public void initEventMetaData(Activity activity, String eventId) {
        eventsRepo.getEvent(activity, eventId, new Callback<Event>() {
            @Override
            public void onSucceed(Event result) {
                event = result;
                setEventMetaDataLive();
            }

            @Override
            public void onFail(CallbackException e) {

            }
        });
    }

    private void setEventMetaDataLive() {
        eventTitleLive.setValue(event.getTitle());
        Date dateBegin = event.getDateBegin();
        if (dateBegin != null)
            eventBeginDateLive.setValue(dateFormat.format(dateBegin));

        Date dateEnd = event.getDateEnd();
        if (dateBegin != null && dateEnd != null)
            eventDurationLive.setValue(getDurationBetweenDate(dateBegin, dateEnd));

        String description = event.getDescription();
        if (description != null)
            eventDescriptionLive.setValue(description);

        Location location = event.getLocation();
        if (location != null)
            eventLocationLive.setValue(getAddressLocalisation(location));

        List<User> participants = event.getParticipants();
        if (participants != null && participants.size() > 0)
            eventParticipantsList.setValue(participants);
    }


    public void loadPosts() {
//        eventsRepo.loadEventPosts( event.getId(), new Callback<Event>() {
//            @Override
//            public void onSucceed(Event result) {
//                event = result;
//                setPostsLive();
//            }
//            @Override
//            public void onFail(CallbackException e) {
//
//            }
//        });
    }


    private void setPostsLive() {
        List<Post> posts = event.getPosts();
        if (posts == null || posts.isEmpty())
            eventPosts.setValue(new ArrayList<>());
        else
            eventPosts.setValue(posts);
    }


    private String getDurationBetweenDate(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime(); // milliseconds
        int days = (int) Math.round(diff / (1000. * 60 * 60 * 24)); // to seconds -> to minutes -> to hours -> to days
        int weeks = (int) Math.round(days / 7.);
        int daysAfterLastWeek = days - Math.round(7 * weeks);

        String duration = "";
        if (weeks >= 1)
            duration += getApplication().getResources().getQuantityString(R.plurals.week_label, weeks, weeks);
        if (daysAfterLastWeek >= 1)
            duration += ((duration.isEmpty() ? "" : ", ") + getApplication().getResources().getQuantityString(R.plurals.day_label, daysAfterLastWeek, daysAfterLastWeek));

        return duration;
    }


    private String getAddressLocalisation(Location localisation) {
        Geocoder geocoder = new Geocoder(this.getApplication().getApplicationContext());
        try {
            List<Address> addresses = geocoder.getFromLocation(localisation.getLatitude(), localisation.getLongitude(), 1);
            if (addresses.size() >= 1) {
                Address a = addresses.get(0);
                String address = "";
                if (a.getLocality() != null) address += a.getLocality();
                if (a.getCountryName() != null)
                    address += ((address.isEmpty() ? "" : ", ") + a.getCountryName());
                if (!address.isEmpty()) return address;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    void requestQuitEvent() {
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
