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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.ui.event_view.adapters.ParticipantsListViewAdapter;
import com.progmobile.meetchup.ui.event_view.adapters.PostsListViewAdapter;
import com.progmobile.meetchup.ui.post_creation.PostCreationActivity;


/**
 * TODO 1: Post contextual menu on long-click
 * TODO 2: Add participant button click listener
 * TODO 3: Loading indication while data loading (meta-data, posts and post documents)
 */
public class FeedFragment extends Fragment {

    private EventViewViewModel viewModel;

    private TextView beginDateView;
    private TextView durationView;
    private RecyclerView participantsView;
    private TextView descriptionView;
    private TextView localisationView;
    private RecyclerView postsView;
    private ViewGroup emptyPostsContainer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() == null)
            throw new RuntimeException("Unexpected fragment creation");

        viewModel = ViewModelProviders.of(getActivity()).get(EventViewViewModel.class);
        viewModel.loadPosts();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_view_feed, container, false);

        this.beginDateView = view.findViewById(R.id.event_begin_date);
        this.durationView = view.findViewById(R.id.event_duration);
        this.descriptionView = view.findViewById(R.id.event_description);
        this.localisationView = view.findViewById(R.id.event_localisation);
        this.participantsView = view.findViewById(R.id.event_participants_list);
        participantsView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.postsView = view.findViewById(R.id.event_posts);
        postsView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        this.emptyPostsContainer = view.findViewById(R.id.event_empty_posts);


        viewModel.eventBeginDateLive.observe(this, begin ->
                beginDateView.setText(begin)
        );

        viewModel.eventDurationLive.observe(this, duration ->
                durationView.setText(duration)
        );

        viewModel.eventDescriptionLive.observe(this, description ->
                descriptionView.setText(description)
        );

        viewModel.eventLocationLive.observe(this, localisation ->
                localisationView.setText(localisation)
        );

        viewModel.eventParticipantsList.observe(this, participants ->
                participantsView.setAdapter(new ParticipantsListViewAdapter(participants))
        );

        viewModel.eventPosts.observe(this, posts -> {
            if (posts.isEmpty()) {
                emptyPostsContainer.setVisibility(View.VISIBLE);
                postsView.setVisibility(View.GONE);
            } else {
                emptyPostsContainer.setVisibility(View.GONE);
                postsView.setVisibility(View.VISIBLE);
                postsView.setAdapter(new PostsListViewAdapter(posts));
            }
        });

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


    private void launchCreationPostActivity() {
        Intent intent = new Intent(getActivity(), PostCreationActivity.class);
        // put extras
        startActivity(intent);
    }

}
