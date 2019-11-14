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
        TextView dateBeginView;
        TextView durationView;
        View eventView;
        Context context;

        EventViewHolder(View itemView, Context context) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.item_event_title);
            this.dateBeginView = itemView.findViewById(R.id.item_event_begin_date);
            this.durationView = itemView.findViewById(R.id.item_event_duration);
            this.eventView = itemView;
            this.context = context;
        }

        public void bind(final Event event, final OnItemClickListener listener){
            eventView.setOnClickListener(v -> listener.onItemClick(event));
            Date dateBegin = event.getDateBegin();
            Date dateEnd = event.getDateEnd();
            String title = event.getTitle();
            if (title != null)
                titleView.setText(title);

            ImageView dateBeginIcon = itemView.findViewById(R.id.item_event_begin_date_icon);
            if (dateBegin != null)
                dateBeginView.setText(dateFormat.format(dateBegin));
            dateBeginIcon.setVisibility(dateBegin == null ? View.GONE : View.VISIBLE);

            ImageView durationIcon = itemView.findViewById(R.id.item_event_duration_icon);
            if (dateBegin != null && dateEnd != null)
                durationView.setText(getDurationBetweenDate(dateBegin, dateEnd));
            durationIcon.setVisibility(dateBegin != null && dateEnd != null ? View.VISIBLE : View.GONE);
        }

        private String getDurationBetweenDate(Date d1, Date d2) {
            long diff = d2.getTime() - d1.getTime(); // milliseconds
            int days = (int) Math.floor(diff / (1000.*60*60*24)); // to seconds -> to minutes -> to hours -> to days
            int weeks = (int) Math.floor(days / 7.);
            int daysAfterLastWeek = days - Math.round(7 * weeks);

            String duration = "";
            if (weeks >= 1)
                duration += context.getResources().getQuantityString(R.plurals.week_label, weeks, weeks);
            if (daysAfterLastWeek >= 1)
                duration += ((duration.isEmpty() ? "" : ", ") +  context.getResources().getQuantityString(R.plurals.day_label, daysAfterLastWeek, daysAfterLastWeek));

            return duration;
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
