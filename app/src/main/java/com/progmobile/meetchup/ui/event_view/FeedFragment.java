package com.progmobile.meetchup.ui.event_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.ListenerRegistration;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.ui.event_view.adapters.ParticipantsListViewAdapter;
import com.progmobile.meetchup.ui.event_view.adapters.PostsListViewAdapter;
import com.progmobile.meetchup.ui.post_creation.PostCreationActivity;
import com.progmobile.meetchup.ui.post_view.PostViewActivity;
import com.progmobile.meetchup.utils.views.EventMetaData;


/**
 *
 */
public class FeedFragment extends Fragment {

    private EventViewViewModel viewModel;

    private EventMetaData eventMetaData;
    private RecyclerView postsView;
    private ViewGroup emptyPostsContainer;

    private ListenerRegistration postListeners;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(EventViewViewModel.class);
        postListeners = viewModel.loadPosts();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_view_feed, container, false);

        eventMetaData = view.findViewById(R.id.event_feed_metadata);

        postsView = view.findViewById(R.id.event_posts);
        postsView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        emptyPostsContainer = view.findViewById(R.id.event_empty_posts);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        postsView.addItemDecoration(divider);


        FloatingActionButton addPostFAB = view.findViewById(R.id.feed_add_post_fab);
        addPostFAB.setOnClickListener(v ->
                launchCreationPostActivity()
        );

        Button addPostButton = view.findViewById(R.id.event_empty_posts_add_btn);
        addPostButton.setOnClickListener(v ->
                launchCreationPostActivity()
        );
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        viewModel.eventMetaData.observe(this, event -> {
            if (event != null)
                eventMetaData.setMetaData(event);
        });

        viewModel.eventPosts.observe(this, posts -> {
            if (posts.isEmpty()) {
                emptyPostsContainer.setVisibility(View.VISIBLE);
                postsView.setVisibility(View.GONE);
            } else {
                emptyPostsContainer.setVisibility(View.GONE);
                postsView.setVisibility(View.VISIBLE);
                postsView.setAdapter(new PostsListViewAdapter(posts, post -> {
                    Intent intent = new Intent(getActivity(), PostViewActivity.class);
                    intent.putExtra(PostViewActivity.EXTRA_POST_ID, post.getId());
                    intent.putExtra(PostViewActivity.EXTRA_EVENT_ID, viewModel.eventID);
                    startActivity(intent);
                }));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if( postListeners != null)
            postListeners.remove();
    }

    private void launchCreationPostActivity() {
        Intent intent = new Intent(getActivity(), PostCreationActivity.class);
        // put extras
        startActivity(intent);
    }

}
