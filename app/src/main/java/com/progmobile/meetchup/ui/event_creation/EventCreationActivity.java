package com.progmobile.meetchup.ui.event_creation;

import android.os.Bundle;

import androidx.annotation.Nullable;


import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.ChildActivity;


/**
 * <h1>EventCreationActivity</h1>
 *
 * <p>Activity to display the event creation form</p>
 *
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
    }

}
