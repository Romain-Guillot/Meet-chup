package com.progmobile.meetchup.ui.invitation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.FormFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;


/**
 * FormFragment that holds the form to see / remove / update the event invitation key
 * All business logic is handled by the InvitationKeyViewModel {@link InvitationKeyViewModel}
 *
 * Here, we initialize the FormFragment with the input field, the form view model, and other
 * configuration data.
 *
 * So the input field is handled with the FormFragment and the FormViewModel
 * {@link com.progmobile.meetchup.utils.FormViewModel}
 *
 * In addition, the form add a switcher to
 */
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

        // View init
        Button disableKeyBtn = view.findViewById(R.id.event_invit_disable);
        TextInputLayout keyFieldLayout = view.findViewById(R.id.event_invit_key_layout);
        Button updateKeyButton = view.findViewById(R.id.event_invit_updatekey);
        TextView keyStatus = view.findViewById(R.id.event_invit_status);

        // Init the FormFragment with the form configuration
        init(
                viewModel,
                Collections.singletonList(keyFieldLayout),
                Collections.singletonList(viewModel.eventKeyFieldLive),
                updateKeyButton,
                "Update invitation key",
                "Loading...",
                "Key updated !"
        );

        // Listener on the disable button
        disableKeyBtn.setOnClickListener( v -> {
            viewModel.removeInvitationKey();
        });

        // Observe when the event key is updated
        viewModel.updateKeyEvent.observe(this, update -> {
            if (update.getContentIfNotHandled())
                keyFieldLayout.getEditText().setText(viewModel.eventKeyFieldLive.getValue());
        });

        // Change the UI according the key status
        viewModel.keyEnabledLive.observe(this, isEnable -> {
            disableKeyBtn.setVisibility( isEnable ? View.VISIBLE : View.GONE);
            keyStatus.setTextColor(getResources().getColor(isEnable ? R.color.successColor : R.color.errorColor));
            keyStatus.setText(isEnable ? R.string.invitation_key_status_enable : R.string.invitation_key_status_disable);
        });

        return view;
    }
}
