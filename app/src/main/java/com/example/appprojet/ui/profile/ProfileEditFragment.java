package com.example.appprojet.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.FormFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;


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

        TextInputLayout emailLayout = view.findViewById(R.id.edit_profile_email);
        setOnFieldChanged(emailLayout, viewModel.emailFormData);
        TextInputLayout usernameLayout = view.findViewById(R.id.edit_profile_username);
        setOnFieldChanged(usernameLayout, viewModel.usernameFormData);
        TextInputLayout newPasswordLayout = view.findViewById(R.id.edit_profile_newpassword);
        setOnFieldChanged(newPasswordLayout, viewModel.newPasswordFormData);
        TextInputLayout newPasswordConfirmLayout = view.findViewById(R.id.edit_profile_newpasswordconfirm);
        setOnFieldChanged(newPasswordConfirmLayout, viewModel.newPasswordConfirmFormData);


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

        List<Button> submitBtns = Arrays.asList(emailSubmitBtn, usernameSubmitBtn, newPasswordSubmitBtn);
        List<LiveData<Boolean>> isLoadingLives = Arrays.asList(viewModel.emailFormIsLoading, viewModel.usernameFormIsLoading, viewModel.newPasswordFormIsLoading);
        for (int i = 0 ; i < submitBtns.size(); i++) {
            final Button btn = submitBtns.get(i);
            final LiveData<Boolean> loadingLive = isLoadingLives.get(i);
            loadingLive.observe(this, isLoading -> {
                btn.setEnabled(!isLoading);
                btn.setText(isLoading ? "Loading ..." : "Update");
            });
        }


        TextView errorView = view.findViewById(R.id.form_error_textview);
        TextView successView = view.findViewById(R.id.form_success_textview);
        viewModel.errorLive.observe(this, errorMessage -> {
            errorView.setVisibility(errorMessage != null ? View.VISIBLE : View.GONE);
            errorView.setText(errorMessage);
        });
        viewModel.successLive.observe(this, successMessage -> {
            successView.setVisibility(successMessage != null ? View.VISIBLE : View.GONE);
            successView.setText(successMessage);
        });

        return view;
    }
}
