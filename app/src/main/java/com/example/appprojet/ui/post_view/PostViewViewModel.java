package com.example.appprojet.ui.post_view;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.Post;
import com.example.appprojet.repositories.FirestoreEventsDataRepository;
import com.example.appprojet.repositories.IEventsDataRepository;
import com.example.appprojet.utils.Callback;

public class PostViewViewModel extends ViewModel {

    private IEventsDataRepository eventRepo;

    MutableLiveData<Post> postLive = new MutableLiveData<>();


    public PostViewViewModel() {
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }


    public void initPost(String id) {
        eventRepo.getPost(id, new Callback<Post>() {
            @Override
            public void onSucceed(Post result) {
                setPostLive(result);
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    private void setPostLive(Post post) {
        if (post == null) {
            Log.d(">>>>>>>>>>>>>>>", "Post null");
        } else {
            postLive.setValue(post);
        }

    }
}
