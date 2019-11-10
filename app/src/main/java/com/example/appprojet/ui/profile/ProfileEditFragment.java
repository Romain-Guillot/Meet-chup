package com.example.appprojet.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.ui.authentication.AuthenticationActivity;
import com.example.appprojet.utils.FormFragment;
import com.example.appprojet.utils.SnackbarFactory;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;


/**
 * Fragment with text fields to edit user profile information :
 *  - email
 *  - name
 *  - password
 *  - (a button) to delete its account
 *
 * It's clearly not the best code, but it works and nothing wrong or bad, it's just redundant with
 * all the text fields. It can be improve.
 *
 * See the edit view model to know more about this fragment business logic
 * {@link ProfileEditViewModel}
 */
public class ProfileEditFragment extends FormFragment {

    private ProfileEditViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ProfileEditViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        // init all text fields layout
        TextInputLayout emailLayout = view.findViewById(R.id.edit_profile_email);
        setOnFieldChanged(emailLayout, viewModel.emailFormData);
        TextInputLayout usernameLayout = view.findViewById(R.id.edit_profile_username);
        setOnFieldChanged(usernameLayout, viewModel.usernameFormData);
        TextInputLayout newPasswordLayout = view.findViewById(R.id.edit_profile_newpassword);
        setOnFieldChanged(newPasswordLayout, viewModel.newPasswordFormData);
        TextInputLayout newPasswordConfirmLayout = view.findViewById(R.id.edit_profile_newpasswordconfirm);
        setOnFieldChanged(newPasswordConfirmLayout, viewModel.newPasswordConfirmFormData);

        // init submits button, add click listeners
        Button emailSubmitBtn = view.findViewById(R.id.profile_edit_submit_email);
        Button usernameSubmitBtn = view.findViewById(R.id.profile_edit_submit_username);
        Button newPasswordSubmitBtn = view.findViewById(R.id.profile_edit_submit_password);
        emailSubmitBtn.setOnClickListener(v -> {
            setLayoutFieldError(emailLayout, viewModel.emailFormData);
            viewModel.submitEmailForm();
        });
        usernameSubmitBtn.setOnClickListener(v -> {
            setLayoutFieldError(usernameLayout, viewModel.usernameFormData);
            viewModel.submitUsernameForm();
        });
        newPasswordSubmitBtn.setOnClickListener(v -> {
            setLayoutFieldError(newPasswordLayout, viewModel.newPasswordFormData);
            setLayoutFieldError(newPasswordConfirmLayout, viewModel.newPasswordConfirmFormData);
            viewModel.submitNewPasswordForm();
        });

        // Listen for loading status and update submits button accordingly
        List<Button> submitBtnList = Arrays.asList(emailSubmitBtn, usernameSubmitBtn, newPasswordSubmitBtn);
        List<LiveData<Boolean>> isLoadingLives = Arrays.asList(viewModel.emailFormIsLoading, viewModel.usernameFormIsLoading, viewModel.newPasswordFormIsLoading);
        for (int i = 0 ; i < submitBtnList.size(); i++) {
            final Button btn = submitBtnList.get(i);
            final LiveData<Boolean> loadingLive = isLoadingLives.get(i);
            loadingLive.observe(this, isLoading -> {
                btn.setEnabled(!isLoading);
                btn.setText(getString(isLoading ? R.string.auth_loading_btn : R.string.profile_edit_update_btn));
            });
        }

        // Delete account button
        Button deleteBtn =  view.findViewById(R.id.profile_edit_delete);
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
}
