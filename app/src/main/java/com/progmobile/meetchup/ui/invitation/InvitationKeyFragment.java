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

import com.google.android.material.textfield.TextInputLayout;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.FormFragment;
import com.progmobile.meetchup.utils.form_views.TextFormLayout;

import java.util.Collections;


/**
 * <p>FormFragment that holds the form to see / remove / update the event invitation key
 * All business logic is handled by the InvitationKeyViewModel {@link InvitationKeyViewModel}</p>
 *
 * <p>Here, we initialize the FormFragment with the input field, the form view model, and other
 * configuration data.</p>
 *
 * <p>So the input field is handled with the FormFragment and the FormViewModel
 * {@link com.progmobile.meetchup.utils.FormViewModel}</p>
 *
 * <p>In addition, the form add a button to remove the current invitation key (if any)</p>
 */
public class InvitationKeyFragment extends FormFragment {

    private InvitationKeyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null)
            throw new RuntimeException("Illegal use of the fragment InvitationKeyFragment");
        viewModel = ViewModelProviders.of(getActivity()).get(InvitationKeyViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_fragment, container, false);

        // View init
        Button disableKeyBtn = view.findViewById(R.id.event_invit_disable);
        Button updateKeyButton = view.findViewById(R.id.event_invit_updatekey);
        TextView keyStatus = view.findViewById(R.id.event_invit_status);

        TextFormLayout keyFieldLayout = view.findViewById(R.id.event_invit_key_layout);
        keyFieldLayout.bindFormData(viewModel.eventKeyFieldLive);

        // Init the FormFragment with the form configuration
        init(
                viewModel,
                Collections.singletonList(keyFieldLayout),
                updateKeyButton,
                getString(R.string.invitation_update_key_btn),
                getString(R.string.loading_btn),
                getString(R.string.invitation_key_update_form_success)
        );

        // Listener on the disable button
        disableKeyBtn.setOnClickListener(v ->
            viewModel.removeInvitationKey()
        );

        // Observe when the event key is updated
        viewModel.updateKeyEvent.observe(this, update -> {
            if (update.getContentIfNotHandled())
                keyFieldLayout.setText(viewModel.eventKeyFieldLive.getValue());
        });

        // Change the UI according the key status
        viewModel.keyEnabledLive.observe(this, isEnable -> {
            disableKeyBtn.setVisibility(isEnable ? View.VISIBLE : View.GONE);
            keyStatus.setTextColor(getResources().getColor(isEnable ? R.color.successColor : R.color.errorColor));
            keyStatus.setText(isEnable ? R.string.invitation_key_status_enable : R.string.invitation_key_status_disable);
        });

        return view;
    }
}
