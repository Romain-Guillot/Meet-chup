package com.example.appprojet.ui.event_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojet.R;


public class FeedFragment extends Fragment {

    private EventViewViewModel viewModel;

    private TextView beginDateView;
    private TextView durationView;
    private RecyclerView participantsView;
    private TextView localisationView;
    private RecyclerView postsView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(EventViewViewModel.class);
        viewModel.loadPosts();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_view_feed, container, false);

        this.beginDateView = view.findViewById(R.id.event_begin_date);
        this.durationView = view.findViewById(R.id.event_duration);
        this.localisationView = view.findViewById(R.id.event_localisation);
        this.participantsView = view.findViewById(R.id.event_participants_list);
        participantsView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.postsView = view.findViewById(R.id.event_posts);
        postsView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        viewModel.eventBeginDateLive.observe(this, begin -> {
            beginDateView.setText(begin);
        });

        viewModel.eventDurationLive.observe(this, duration -> {
            durationView.setText(duration);
        });

        viewModel.eventLocalisationLive.observe(this, localisation -> {
            localisationView.setText(localisation);
        });

        viewModel.eventParticipantsList.observe(this, participants -> {
            participantsView.setAdapter(new ParticipantsListViewAdapter(participants));
        });

        viewModel.eventPosts.observe(this, posts -> {
            postsView.setAdapter(new PostsListViewAdapter(posts));
        });




        return view;
    }



}
