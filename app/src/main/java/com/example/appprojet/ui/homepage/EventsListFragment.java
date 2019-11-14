package com.example.appprojet.ui.homepage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojet.R;


/**
 * Handle the user list of events
 */
public class EventsListFragment extends Fragment {

    HomepageViewModel viewModel;

    private RecyclerView eventsView;
    private ViewGroup emptyEventsContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(HomepageViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage_events_list, container, false);

        eventsView = view.findViewById(R.id.user_events);
        eventsView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        emptyEventsContainer = view.findViewById(R.id.empty_event_list);

        viewModel.userEventsLive.observe(this, events -> {
            if(events.isEmpty()){
                emptyEventsContainer.setVisibility(View.VISIBLE);
                eventsView.setVisibility(View.GONE);
            } else {
                emptyEventsContainer.setVisibility(View.GONE);
                eventsView.setVisibility(View.VISIBLE);
                eventsView.setAdapter(new EventsListViewAdapter(events));
            }
        });

        return view;
    }
}
