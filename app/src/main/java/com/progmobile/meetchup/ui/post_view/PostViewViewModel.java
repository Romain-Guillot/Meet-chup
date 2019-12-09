package com.progmobile.meetchup.ui.post_view;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.ListenerRegistration;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.repositories.IEventsDataRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;


public class PostViewViewModel extends ViewModel {

    MutableLiveData<Post> postLive = new MutableLiveData<>();
    private IEventsDataRepository eventRepo;


    public PostViewViewModel() {
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }


    public ListenerRegistration initPost(String eventID, String postID) {
        return eventRepo.getPost(eventID, postID, new Callback<Post>() {
            public void onSucceed(Post result) {
                postLive.setValue(result);
            }

            public void onFail(CallbackException exception) {
                postLive.setValue(null);
            }
        });
    }

}
