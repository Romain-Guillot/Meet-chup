package com.example.appprojet.ui.authentication.sign_auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.ui.authentication.FormFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SignUpFragment extends FormFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_signup, container, false);

        SignUpViewModel viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

        TextInputLayout emailLayout = view.findViewById(R.id.auth_signup_email_layout);
        TextInputLayout passwordLayout = view.findViewById(R.id.auth_signup_password_layout);
        TextInputLayout passwordConfirmLayout = view.findViewById(R.id.auth_signup_password_confirm_layout);

        init(
                view,
                viewModel,
                Arrays.asList(emailLayout, passwordLayout, passwordConfirmLayout),
                Arrays.asList(viewModel.emailLive, viewModel.passwordLive, viewModel.passwordConfirmLive)
        );

        return view;
    }
}
