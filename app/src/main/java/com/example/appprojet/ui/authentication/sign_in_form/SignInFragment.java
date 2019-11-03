package com.example.appprojet.ui.authentication.sign_in_form;

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
 * Subclass of FormFragment to handle the sign-in form.
 * See the FormFragment documentation to more details.
 *
 * This fragment retrieve the following SignInViewModel associated.
 * It also init the form fields layout ...
 *  - email
 *  - password
 * ... and init the FormFragment with these layouts and the FormData associated in the
 * SignInViewModel.
 */
public class SignInFragment extends FormFragment {

    private SignInViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We "link" the view model to the activity to keep the same view model, even is the fragment instance change
        viewModel = ViewModelProviders.of(getActivity()).get(SignInViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_signin, container, false);

        TextInputLayout emailLayout = view.findViewById(R.id.auth_signin_email_layout);
        TextInputLayout passwordLayout = view.findViewById(R.id.auth_signin_password_layout);

        init(
                view,
                viewModel,
                Arrays.asList(emailLayout, passwordLayout),
                Arrays.asList(viewModel.emailLive, viewModel.passwordLive),
                getString(R.string.auth_signin_btn),
                getString(R.string.auth_loading_btn)
        );

        return view;
    }
}
