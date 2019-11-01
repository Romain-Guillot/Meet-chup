package com.example.appprojet.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.ui.authentication.custom_live_data.FormMutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

public class SignInFragment extends Fragment {

    SignInViewModel viewModel;

    private static int GOOGLE_SIGN_IN = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() == null)
            throw new RuntimeException();

        // We "link" the view model to the activity to keep the same view model, even is the fragment instance change
        viewModel = ViewModelProviders.of(getActivity()).get(SignInViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_signin, container, false);

        TextInputLayout emailLayout = view.findViewById(R.id.auth_signin_email_layout);
        setOnFieldChanged(emailLayout, viewModel.emailLive);

        TextInputLayout passwordLayout = view.findViewById(R.id.auth_signin_password_layout);
        setOnFieldChanged(passwordLayout, viewModel.passwordLive);

        Button submitButton = view.findViewById(R.id.auth_signin_submit);
        submitButton.setOnClickListener(v -> {
            viewModel.submitForm();
            setLayoutFieldError(emailLayout, viewModel.emailLive);
            setLayoutFieldError(passwordLayout, viewModel.passwordLive);
        });

        viewModel.isLoadingLive.observe(this, isLoading -> {
            submitButton.setEnabled(!isLoading);
            submitButton.setText(
                    isLoading ? "Loading" : "Log in"
            );
        });

        TextView errorTextView = view.findViewById(R.id.auth_signin_error);
        viewModel.errorLive.observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                errorTextView.setText(error);
            } else {
                errorTextView.setVisibility(View.GONE);
            }
        });

        viewModel.isLoggedLive.observe(this, isLogged -> {
            if (isLogged)
                AuthenticationActivity.obtainViewModel(getActivity()).finish();
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.google_web_client_id))
                .build();
        GoogleSignInClient googleSignIn = GoogleSignIn.getClient(getActivity(), gso);

        Button googleSignInButton = view.findViewById(R.id.sign_in_google);
        googleSignInButton.setOnClickListener(v -> {
            signInWithGoogle(googleSignIn);
        });

        return view;
    }


    private void setOnFieldChanged(TextInputLayout editTextLayout, FormMutableLiveData editTextLiveData) {
        EditText editText = editTextLayout.getEditText();
        editText.setText(editTextLiveData.getValue());

        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextLiveData.setValue(s.toString());
            }
        });

        editText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) setLayoutFieldError(editTextLayout, editTextLiveData);
            else editTextLayout.setError(null);
        });
    }

    private void setLayoutFieldError(TextInputLayout layout, FormMutableLiveData formMutableLiveData) {
        layout.setError(!formMutableLiveData.isValid() ? formMutableLiveData.getError() : null);
    }

    private void signInWithGoogle(GoogleSignInClient googleSignInClient) {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                viewModel.requestGoogleSignIn(googleSignInAccount);
            } catch (ApiException e) {
                e.printStackTrace(); // update ui
            }
        }
    }
}
