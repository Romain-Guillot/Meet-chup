package com.example.appprojet.ui.event_view.invitation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.FormFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Collections;

public class InvitationKeyFragment extends FormFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_fragment, container, false);

        InvitationKeyViewModel viewModel = ViewModelProviders.of(getActivity()).get(InvitationKeyViewModel.class);

        SwitchMaterial enableKeySwitch = view.findViewById(R.id.event_invit_enablekey);
        TextInputLayout keyFieldLayout = view.findViewById(R.id.event_invit_keyfield);
        Button updateKeyButton = view.findViewById(R.id.event_invit_updatekey);

        init(
                viewModel,
                Collections.singletonList(keyFieldLayout),
                Collections.singletonList(viewModel.eventKeyLive),
                updateKeyButton,
                "Update invitation key",
                "Loading..."
        );

        enableKeySwitch.setOnCheckedChangeListener( (v, isChecked) -> {
            if (!isChecked) {
                viewModel.removeInvitationKey();
            }
        });

        viewModel.updateKeyField.observe(this, update -> {
            if (update.getContentIfNotHandled())
                keyFieldLayout.getEditText().setText(viewModel.eventKeyLive.getValue());
        });

        viewModel.keyEnabledLive.observe(this, isEnable -> {
            enableKeySwitch.setChecked(isEnable);
        });



        return view;
    }
}
