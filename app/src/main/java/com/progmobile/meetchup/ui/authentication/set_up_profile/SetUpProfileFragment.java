package com.progmobile.meetchup.ui.authentication.set_up_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.ui.authentication.AuthenticationActivity;
import com.progmobile.meetchup.utils.FormFragment;

import java.util.Collections;


/**
 * Subclass of FormFragment to handle the set up profile form.
 * See the FormFragment documentation to more details.
 * <p>
 * This fragment retrieve the SetUpProfileViewModel associated.
 * It also init the form following fields layout ...
 * - username
 * ... and init the FormFragment with these layouts and the FormData associated in the
 * SetUpProfileViewModel.
 */
public class SetUpProfileFragment extends FormFragment {

    private SetUpProfileViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We "link" the view model to the activity to keep the same view model, even is the fragment instance change
        viewModel = ViewModelProviders.of(getActivity()).get(SetUpProfileViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_setup, container, false);

        TextInputLayout nameLayout = view.findViewById(R.id.auth_setup_name_layout);
        Button submitButton = view.findViewById(R.id.form_submit_btn);

//        init(
//                viewModel,
//                Collections.singletonList(nameLayout),
//                Collections.singletonList(viewModel.nameLive),
//                submitButton,
//                getString(R.string.auth_set_up_btn),
//                getString(R.string.loading_btn),
//                null
//        );TODO

        // Notify the authentication activity that the authentication process is done.
        viewModel.isFinish.observe(this, isFinish -> {
            if (isFinish)
                AuthenticationActivity.obtainViewModel(getActivity()).finish();
        });

        return view;
    }
}
