package com.example.appprojet.ui.event_view.invitation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.ChildActivity;


public class InvitationKeyActivity extends ChildActivity {

    public static final String EXTRA_EVENT_ID = "com.example.appprojet.event_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("Invitation key");
        setContentView(R.layout.activity_event_view_invit_key);

        Intent srcIntent = getIntent();
        String eventKey = srcIntent.getStringExtra(EXTRA_EVENT_ID);

        InvitationKeyViewModel viewModel = ViewModelProviders.of(this).get(InvitationKeyViewModel.class);
        viewModel.init(eventKey);



    }
}
