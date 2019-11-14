package com.progmobile.meetchup.ui.invitation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.ChildActivity;


/**
 * <h1>InvitationKeyActivity</h1>
 *
 * <p>This activity holds the main fragment : {@link InvitationKeyFragment}
 * It also initializes the view model with the event ID (see INTENT COMMUNICATION below)</p>
 *
 * <h2>INTENT COMMUNICATION</h2>
 * <p>The intent extra string is the event ID and the extra name has to be [EXTRA_EVENT_ID].</p>
 */
public class InvitationKeyActivity extends ChildActivity {

    public static final String EXTRA_EVENT_ID = "com.progmobile.meetchup.event_id";

    private String eventID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("Invitation key");
        setContentView(R.layout.activity_event_view_invit_key);

        // Retrieve the src intent, throw an exception if extra ID is put in the intent
        Intent srcIntent = getIntent();
        eventID = srcIntent.getStringExtra(EXTRA_EVENT_ID);
        if (eventID == null)
            throw new RuntimeException("Event ID missing");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Init the view model
        InvitationKeyViewModel viewModel = ViewModelProviders.of(this).get(InvitationKeyViewModel.class);
        viewModel.init(this, eventID);
    }
}
