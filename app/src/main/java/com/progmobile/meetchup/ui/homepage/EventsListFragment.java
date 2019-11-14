package com.progmobile.meetchup.ui.homepage;

import android.content.Intent;
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

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.ui.event_view.EventViewActivity;


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
        Intent intent = new Intent(getActivity(), EventViewActivity.class);
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
                eventsView.setAdapter(new EventsListViewAdapter(events, new EventsListViewAdapter.OnItemClickListener() {
                    @Override public void onItemClick(Event event) {
                        intent.putExtra(EventViewActivity.EXTRA_EVENT_ID, event.getId());
                        startActivity(intent);
                    }
                }));
            }
        });

        return view;
    }
}
