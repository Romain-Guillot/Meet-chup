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

import java.util.Date;
import java.util.List;

public class EventsListViewAdapter extends RecyclerView.Adapter<EventsListViewAdapter.EventViewHolder> {

    List<Event> events;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView dateBeginView;
        TextView durationView;
        EventViewHolder(View itemView) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.item_event_title);
            this.dateBeginView = itemView.findViewById(R.id.item_event_begin_date);
            this.durationView = itemView.findViewById(R.id.item_event_duration);
        }
    }

    public EventsListViewAdapter(List<Event> events) {
        this.events = events;
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

        Date dateBegin = event.getDateBegin();
        Date dateEnd = event.getDateEnd();
        String title = event.getTitle();
        if (title != null)
            holder.titleView.setText(title);
        if (dateBegin != null)
            Log.d(">>>>>>>>>>DATE",dateBegin.toString());
            holder.dateBeginView.setText(dateBegin.toString());
        if (dateBegin != null && dateEnd != null){
            Log.d(">>>>>>>>>>DATE",dateEnd.toString());
            long duration = dateEnd.getTime() - dateBegin.getTime();
            long days = duration/1000/60/60/24;
            Log.d(">>>>>>>>>>DURATION",days + " days");
            holder.durationView.setText(days + " days");
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
