package com.example.appprojet.ui.event_view.invitation;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.appprojet.R;
import com.example.appprojet.utils.ChildActivity;

public class InvitationKeyActivity extends ChildActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("Invitation key");
        setContentView(R.layout.activity_event_view_invit_key);
    }
}
