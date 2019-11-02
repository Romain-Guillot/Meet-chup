package com.example.appprojet.ui.authentication.sign_up_form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.ui.authentication.FormFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;


/**
 *
 */
public class SignUpFragment extends FormFragment {

    private SignUpViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We "link" the view model to the activity to keep the same view model, even is the fragment instance change
        viewModel = ViewModelProviders.of(getActivity()).get(SignUpViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_signup, container, false);

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
