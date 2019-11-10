package com.example.appprojet.ui.event_view.invitation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.FormFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;


public class InvitationKeyFragment extends FormFragment {

    private InvitationKeyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(InvitationKeyViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_fragment, container, false);

        SwitchMaterial enableKeySwitch = view.findViewById(R.id.event_invit_enablekey);
        TextInputLayout keyFieldLayout = view.findViewById(R.id.event_invit_key_layout);
        Button updateKeyButton = view.findViewById(R.id.event_invit_updatekey);

        init(
                viewModel,
                Collections.singletonList(keyFieldLayout),
                Collections.singletonList(viewModel.eventKeyFieldLive),
                updateKeyButton,
                "Update invitation key",
                "Loading...",
                "Key updated !"
        );

        enableKeySwitch.setOnCheckedChangeListener( (v, isChecked) -> {
            if (!isChecked)
                viewModel.removeInvitationKey();
        });

        viewModel.updateKeyEvent.observe(this, update -> {
            if (update.getContentIfNotHandled())
                keyFieldLayout.getEditText().setText(viewModel.eventKeyFieldLive.getValue());
        });

        viewModel.keyEnabledLive.observe(this, isEnable -> {
            enableKeySwitch.setChecked(isEnable);
            int color = isEnable ? R.color.successColor : R.color.errorColor;
            int colorLight = isEnable ? R.color.successColorLight : R.color.errorColorLight;
            enableKeySwitch.setTrackTintList(ContextCompat.getColorStateList(getActivity(), colorLight));
            enableKeySwitch.setThumbTintList(ContextCompat.getColorStateList(getActivity(), color));
            enableKeySwitch.setTextColor(ContextCompat.getColorStateList(getActivity(), color));
            enableKeySwitch.setText(isEnable ? R.string.invitation_key_status_enable : R.string.invitation_key_status_disable);
        });

        return view;
    }
}
