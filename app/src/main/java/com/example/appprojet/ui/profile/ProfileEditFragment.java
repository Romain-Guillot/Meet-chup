package com.example.appprojet.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.FormFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;


public class ProfileEditFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfileEditCredentialFragment.viewModel = ViewModelProviders.of(getActivity()).get(ProfileEditCredentialViewModel.class);
        ProfileEditProfileFragment.viewModel = ViewModelProviders.of(getActivity()).get(ProfileEditProfileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }


    public static class ProfileEditCredentialFragment extends FormFragment {
        public static ProfileEditCredentialViewModel viewModel;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile_edit_credentials, container, false);

            TextInputLayout passwordLayout = view.findViewById(R.id.edit_profile_password);
            TextInputLayout emailLayout = view.findViewById(R.id.edit_profile_email);
            TextInputLayout newPasswordLayout = view.findViewById(R.id.edit_profile_newpassword);
            TextInputLayout newPasswordConfirmationLayout = view.findViewById(R.id.edit_profile_newpasswordconfirm);

            init(
                    view,
                    viewModel,
                    Arrays.asList(passwordLayout, emailLayout, newPasswordLayout, newPasswordConfirmationLayout),
                    Arrays.asList(viewModel.passwordFormData, viewModel.emailFormData, viewModel.newPasswordFormData, viewModel.newPasswordFormDataConfirmation),
                    "Update credentials",
                    "Loading ..."
            );

            return view;
        }
    }

    public static class ProfileEditProfileFragment extends FormFragment {
        public static ProfileEditProfileViewModel viewModel;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile_edit_profile, container, false);

            TextInputLayout nameLayout = view.findViewById(R.id.edit_profile_username);

            init(
                    view,
                    viewModel,
                    Arrays.asList(nameLayout),
                    Arrays.asList(viewModel.usernameFormData),
                    "Update profile",
                    "Loading ..."
            );

            return view;
        }
    }


}
