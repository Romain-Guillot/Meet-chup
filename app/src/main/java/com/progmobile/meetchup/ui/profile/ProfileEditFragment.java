package com.progmobile.meetchup.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.ui.authentication.AuthenticationActivity;
import com.progmobile.meetchup.utils.FormFragment;
import com.progmobile.meetchup.utils.SnackbarFactory;
import com.progmobile.meetchup.utils.form_views.TextFormLayout;

import java.util.Arrays;
import java.util.List;


/**
 * <p>Fragment with text fields to edit user profile information :</p>
 *
 * <ul>
 *     <li>email</li>
 *     <li>name</li>
 *     <li>password</li>
 *     <li>(a button) to delete its account</li>
 * </ul>

 * <p> It's clearly not the best code, but it works and nothing wrong or bad, it's just redundant
 * with all the text fields. It can be improve.</p>
 *
 * <p>See the edit view model to know more about this fragment business logic
 * {@link ProfileEditViewModel} </p>
 */
public class ProfileEditFragment extends FormFragment {

    private ProfileEditViewModel viewModel;

    private TextFormLayout emailLayout;
    private TextFormLayout usernameLayout;
    private TextFormLayout passwordLayout;
    private TextFormLayout passwordConfirmLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null)
            throw new RuntimeException("Illegal use of ProfileEditFragment");
        viewModel = ViewModelProviders.of(getActivity()).get(ProfileEditViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        // init all text fields layout
        emailLayout =  view.findViewById(R.id.edit_profile_email_layout);
        usernameLayout = view.findViewById(R.id.edit_profile_username_layout);
        passwordLayout = view.findViewById(R.id.edit_profile_newpassword_layout);
        passwordConfirmLayout = view.findViewById(R.id.edit_profile_newpasswordconfirm_layout);

        // init submits button, add click listeners
        Button emailSubmitBtn = view.findViewById(R.id.profile_edit_submit_email);
        Button usernameSubmitBtn = view.findViewById(R.id.profile_edit_submit_username);
        Button newPasswordSubmitBtn = view.findViewById(R.id.profile_edit_submit_password);
        emailSubmitBtn.setOnClickListener(v -> {
            emailLayout.setLayoutError();
            viewModel.submitEmailForm();
        });
        usernameSubmitBtn.setOnClickListener(v -> {
            usernameLayout.setLayoutError();
            viewModel.submitUsernameForm();
        });
        newPasswordSubmitBtn.setOnClickListener(v -> {
            passwordLayout.setLayoutError();
            passwordConfirmLayout.setLayoutError();
            viewModel.submitNewPasswordForm();
        });

        // Listen for loading status and update submits button accordingly
        List<Button> submitBtnList = Arrays.asList(emailSubmitBtn, usernameSubmitBtn, newPasswordSubmitBtn);
        List<LiveData<Boolean>> isLoadingLives = Arrays.asList(viewModel.emailFormIsLoading, viewModel.usernameFormIsLoading, viewModel.newPasswordFormIsLoading);
        for (int i = 0; i < submitBtnList.size(); i++) {
            final Button btn = submitBtnList.get(i);
            final LiveData<Boolean> loadingLive = isLoadingLives.get(i);
            loadingLive.observe(this, isLoading -> {
                btn.setEnabled(!isLoading);
                btn.setText(getString(isLoading ? R.string.loading_btn : R.string.profile_edit_update_btn));
            });
        }

        // Delete account button
        Button deleteBtn = view.findViewById(R.id.profile_edit_delete);
        SwitchMaterial deleteConfirmSwitch = view.findViewById(R.id.profile_edit_delete_confirm);
        deleteBtn.setEnabled(deleteConfirmSwitch.isChecked());
        deleteConfirmSwitch.setOnCheckedChangeListener((v, isChecked) ->
                deleteBtn.setEnabled(isChecked)
        );
        deleteBtn.setOnClickListener(v -> {
            if (deleteConfirmSwitch.isChecked())
                viewModel.deleteAccount();
        });
        viewModel.isDeleted.observe(this, isDeleted -> {
            if (isDeleted) {
                Intent authIntent = new Intent(getActivity(), AuthenticationActivity.class);
                authIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(authIntent);
            }
        });

        // Set error or info messages if any
        viewModel.errorLive.observe(this, errorMessage -> {
            if (errorMessage != null) {
                String message = errorMessage.getContentIfNotHandled();
                if (message != null)
                    SnackbarFactory.showErrorSnackbar(getActivity().findViewById(android.R.id.content), message);
            }
        });
        viewModel.successLive.observe(this, successMessage -> {
            if (successMessage != null) {
                String message = successMessage.getContentIfNotHandled();
                if (message != null)
                    SnackbarFactory.showSuccessSnackbar(getActivity().findViewById(android.R.id.content), message);
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        viewModel.init();
        emailLayout.bindFormData(viewModel.emailFormData);
        usernameLayout.bindFormData(viewModel.usernameFormData);
        passwordLayout.bindFormData(viewModel.newPasswordFormData);
        passwordConfirmLayout.bindFormData(viewModel.newPasswordConfirmFormData);
    }
}
