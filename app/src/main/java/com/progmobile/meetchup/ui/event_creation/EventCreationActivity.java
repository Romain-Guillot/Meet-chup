package com.progmobile.meetchup.ui.event_creation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;


import com.progmobile.meetchup.R;
import com.progmobile.meetchup.ui.event_view.EventViewActivity;
import com.progmobile.meetchup.utils.ChildActivity;


/**
 * <h1>EventCreationActivity</h1>
 *
 * <p>Activity to display the event creation form</p>
 *
 * <p>When the event is create, we start the EventViewActivity to see the fragment</p>
 *
 * <h2>INTENT COMMUNICATION</h2>
 * <p>No extra data required.</p>
 *
 *
 * <h2>BUGS</h2>
 * <p>None</p>
 */
public class EventCreationActivity extends ChildActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("New event");
        setContentView(R.layout.acrivity_event_creation);

        EventCreationViewModel viewModel = ViewModelProviders.of(this).get(EventCreationViewModel.class);
        // when the event is created we launch the event view activity to see it
        viewModel.eventCreated.observe(this, successEvent -> {
            if (successEvent != null) {
                Intent goToEvent = new Intent(this, EventViewActivity.class);
                goToEvent.putExtra(EventViewActivity.EXTRA_EVENT_ID, successEvent);
                startActivity(goToEvent);
                finish();
            }
        });
    }

}
