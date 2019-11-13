package com.example.appprojet.ui.authentication.sign_up_form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.FormFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;


/**
 * Subclass of FormFragment to handle the sign-up form.
 * See the FormFragment documentation to more details.
 *
 * This fragment retrieve the SignUpViewModel associated.
 * It also init the following form fields layout ...
 *  - email
 *  - password
 *  - password confirmation
 * ... and init the FormFragment with these layouts and the FormData associated in the
 * SignUpViewModel.
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

        Button submitButton = view.findViewById(R.id.form_submit_btn);

//        init(
//                viewModel,
//                Arrays.asList(emailLayout, passwordLayout, passwordConfirmLayout),
//                Arrays.asList(viewModel.emailLive, viewModel.passwordLive, viewModel.passwordConfirmLive),
//                submitButton,
//                getString(R.string.auth_signup_btn),
//                getString(R.string.loading_btn),
//                null
//        );TODO

        return view;
    }
}
