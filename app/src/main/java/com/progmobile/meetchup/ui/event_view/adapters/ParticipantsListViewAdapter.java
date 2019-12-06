package com.progmobile.meetchup.ui.event_view.adapters;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progmobile.meetchup.models.User;

import java.util.List;


/**
 *
 */
public class ParticipantsListViewAdapter extends RecyclerView.Adapter<ParticipantsListViewAdapter.ParticipantViewHolder> {

    private List<User> participants;

    public ParticipantsListViewAdapter(List<User> participants) {
        this.participants = participants;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = new TextView(parent.getContext());
        ParticipantViewHolder viewHolder = new ParticipantViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder postViewHolder, int position) {
        postViewHolder.userName.setText(participants.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.participants.size();
    }

    public static class ParticipantViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;

        public ParticipantViewHolder(TextView userName) {
            super(userName);
            this.userName = userName;
        }
    }
}
