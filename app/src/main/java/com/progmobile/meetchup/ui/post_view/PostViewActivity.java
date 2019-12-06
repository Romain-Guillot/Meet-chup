package com.progmobile.meetchup.ui.post_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.firestore.ListenerRegistration;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.ui.event_view.adapters.PostsListViewAdapter;
import com.progmobile.meetchup.ui.homepage.EventsListViewAdapter;
import com.progmobile.meetchup.utils.ChildActivity;


public class PostViewActivity extends ChildActivity {

    public static final String EXTRA_EVENT_ID = "event_id";
    public static final String EXTRA_POST_ID = "post_id";

    private String post_id = null;
    private String event_id = null;
    private ListenerRegistration postListener = null;

    private PostViewViewModel viewModel;

    FrameLayout container;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        setActionBarTitle("Post");


        Intent intent = getIntent();
        post_id = intent.getStringExtra(EXTRA_POST_ID);
        event_id = intent.getStringExtra(EXTRA_EVENT_ID);

        if (post_id == null || event_id == null)
            throw new RuntimeException();

        viewModel = ViewModelProviders.of(this).get(PostViewViewModel.class);

        container = findViewById(R.id.post_content);


    }

    @Override
    protected void onStart() {
        super.onStart();
        postListener = viewModel.initPost(event_id, post_id);
        View view = getLayoutInflater().inflate(R.layout.item_post, container);
        PostsListViewAdapter.PostViewHolder postViewHolder = new PostsListViewAdapter.PostViewHolder(view);
        viewModel.postLive.observe(this, post -> {
            postViewHolder.bind(post, null);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (postListener != null)
            postListener.remove();

    }
}
