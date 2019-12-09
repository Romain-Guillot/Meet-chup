package com.progmobile.meetchup.ui.post_view;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.ListenerRegistration;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.repositories.IEventsDataRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.SingleEvent;


public class PostViewViewModel extends ViewModel {

    String eventID = null;
    String postID = null;
    MutableLiveData<Post> postLive = new MutableLiveData<>();
    MutableLiveData<SingleEvent<Boolean>> deletePostEvent = new MutableLiveData<>(); // true if success, false else
    private IEventsDataRepository eventRepo;


    public PostViewViewModel() {
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }


    public ListenerRegistration initPost(String eventID, String postID) {
        this.eventID = eventID;
        this.postID = postID;
        return eventRepo.getPost(eventID, postID, new Callback<Post>() {
            public void onSucceed(Post result) {
                postLive.setValue(result);
            }

            public void onFail(CallbackException exception) {
                postLive.setValue(null);
            }
        });
    }

    public void deletePost() {
        if( postID != null && eventID != null) {
            eventRepo.deletePost(eventID, postID, new Callback<Void>() {
                public void onSucceed(Void result) {
                    deletePostEvent.setValue(new SingleEvent<>(true));
                }
                public void onFail(CallbackException exception) {
                    deletePostEvent.setValue(new SingleEvent<>(false));
                }
            });
        } else {
            deletePostEvent.setValue(new SingleEvent<>(false));
        }
    }

}
