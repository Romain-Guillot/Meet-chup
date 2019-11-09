package com.example.appprojet.ui.event_view.invitation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.ChildActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

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

        SwitchMaterial enableKeySwitch = findViewById(R.id.event_invit_enablekey);
        TextInputLayout keyFieldLayout = findViewById(R.id.event_invit_keyfield);
        Button updateKeyButton = findViewById(R.id.event_invit_updatekey);

        enableKeySwitch.setOnCheckedChangeListener( (v, isChecked) -> {

        });

        updateKeyButton.setOnClickListener(v -> {

        });

    }
}
