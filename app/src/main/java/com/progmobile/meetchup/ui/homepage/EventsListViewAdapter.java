package com.progmobile.meetchup.ui.homepage;

import android.content.Context;
import android.service.autofill.VisibilitySetterAction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.utils.DurationUtils;
import com.progmobile.meetchup.utils.views.EventMetaData;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class EventsListViewAdapter extends RecyclerView.Adapter<EventsListViewAdapter.EventViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Event event);
    }

    private static DateFormat dateFormat = DateFormat.getDateInstance();

    List<Event> events;
    OnItemClickListener listener;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        EventMetaData metaDataView;
        View eventView;
        Context context;

        EventViewHolder(View itemView, Context context) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.item_event_title);
            this.metaDataView = itemView.findViewById(R.id.item_event_metadata);

            this.eventView = itemView;
            this.context = context;
        }

        public void bind(final Event event, final OnItemClickListener listener){
            eventView.setOnClickListener(v -> listener.onItemClick(event));

            String title = event.getTitle();
            if (title != null)
                titleView.setText(title);

            metaDataView.setMetaData(event);
        }
    }

    public EventsListViewAdapter(List<Event> events, OnItemClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventsListViewAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventView = inflater.inflate(R.layout.item_event, parent, false);
        EventsListViewAdapter.EventViewHolder holder = new EventsListViewAdapter.EventViewHolder(eventView, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventsListViewAdapter.EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event, listener);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
