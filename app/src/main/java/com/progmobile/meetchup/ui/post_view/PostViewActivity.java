package com.progmobile.meetchup.ui.post_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.ListenerRegistration;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.ui.event_view.adapters.PostsListViewAdapter;
import com.progmobile.meetchup.ui.invitation.InvitationKeyActivity;
import com.progmobile.meetchup.utils.ChildActivity;
import com.progmobile.meetchup.utils.SnackbarFactory;


public class PostViewActivity extends ChildActivity {

    public static final String EXTRA_EVENT_ID = "event_id";
    public static final String EXTRA_POST_ID = "post_id";
    FrameLayout container;
    private String post_id = null;
    private String event_id = null;
    private ListenerRegistration postListener = null;
    private PostViewViewModel viewModel;

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

        viewModel.deletePostEvent.observe(this, event -> {
            Boolean status = event.getContentIfNotHandled();
            if ( status!= null) {
                if (status == true) {
                    finish();
                } else {
                    SnackbarFactory.showErrorSnackbar(findViewById(android.R.id.content), getString(R.string.post_delete_error));
                }
            }
        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_view_delete:
                viewModel.deletePost();
                SnackbarFactory.showSuccessSnackbar(findViewById(android.R.id.content), getString(R.string.post_delete_loading));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
