package com.progmobile.meetchup.ui.post_view;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.repositories.IEventsDataRepository;

import java.util.List;

public class PostViewViewModel extends ViewModel {

    private IEventsDataRepository eventRepo;

    MutableLiveData<String> postDescriptionLive = new MutableLiveData<>();
    MutableLiveData<String> postImageLive = new MutableLiveData<>();
    MutableLiveData<List> postCommentsLive = new MutableLiveData<>();



    public PostViewViewModel() {
        eventRepo = FirestoreEventsDataRepository.getInstance();
    }


    public void initPost(String id) {
//        eventRepo.getPost(id, new Callback<Post>() {
//            @Override
//            public void onSucceed(Post result) {
//                setPostLive(result);
//                eventRepo.loadPostComments(result, new Callback<Post>() {
//                    @Override
//                    public void onSucceed(Post result) {
//                        setCommentsLive(result);
//                    }
//
//                    @Override
//                    public void onFail(CallbackException e) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFail(CallbackException e) {
//
//            }
//        });

    }



    private void setPostLive(Post post) {
        if (post == null) {
            Log.d(">>>>>>>>>>>>>>>", "Post null");
        } else {
            postDescriptionLive.setValue(post.getDescription());
            postImageLive.setValue(post.getDocument().getUrl());
        }
    }

    private void setCommentsLive(Post post) {
        if (post == null) {
            Log.d(">>>>>>>>>>>>>>>", "Post null");
        } else {
            postCommentsLive.setValue(post.getCommentsList());
        }
    }
}
