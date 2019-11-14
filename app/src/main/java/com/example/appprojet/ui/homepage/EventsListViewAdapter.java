package com.example.appprojet.ui.homepage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojet.R;
import com.example.appprojet.models.Event;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class EventsListViewAdapter extends RecyclerView.Adapter<EventsListViewAdapter.EventViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Event event);
    }

    private DateFormat dateFormat = DateFormat.getDateInstance();

    List<Event> events;
    OnItemClickListener listener;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView dateBeginView;
        TextView durationView;
        View eventView;

        EventViewHolder(View itemView) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.item_event_title);
            this.dateBeginView = itemView.findViewById(R.id.item_event_begin_date);
            this.durationView = itemView.findViewById(R.id.item_event_duration);
            this.eventView = itemView;
        }

        public void bind(final Event event, final OnItemClickListener listener){
            eventView.setOnClickListener(v -> listener.onItemClick(event));
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
        EventsListViewAdapter.EventViewHolder holder = new EventsListViewAdapter.EventViewHolder(eventView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventsListViewAdapter.EventViewHolder holder, int position) {
        Event event = events.get(position);

        holder.bind(event, listener);
        Date dateBegin = event.getDateBegin();
        Date dateEnd = event.getDateEnd();
        String title = event.getTitle();
        if (title != null)
            holder.titleView.setText(title);
        if (dateBegin != null)
            holder.dateBeginView.setText(dateFormat.format(dateBegin));
        if (dateBegin != null && dateEnd != null){
            long duration = dateEnd.getTime() - dateBegin.getTime();
            long days = duration/1000/60/60/24;
            holder.durationView.setText(days + " days");
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
