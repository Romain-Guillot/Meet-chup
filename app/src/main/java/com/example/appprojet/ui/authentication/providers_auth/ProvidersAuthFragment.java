package com.example.appprojet.ui.authentication.providers_auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


/**
 *
 */
public class ProvidersAuthFragment extends Fragment {

    private ProvidersAuthViewModel viewModel;

    private static final int GOOGLE_SIGN_IN = 1;
    private static final int FACEBOOK_SIGN_IN = 2;
    private static final int TWITTER_SIGN_IN = 3;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() == null)
            throw new RuntimeException("Unexpected fragment creation");

        viewModel = ViewModelProviders.of(getActivity()).get(ProvidersAuthViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_providers, container, false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.google_web_client_id))
                .build();
        GoogleSignInClient googleSignIn = GoogleSignIn.getClient(getActivity(), gso);

        Button googleSignInButton = view.findViewById(R.id.sign_in_google);

        googleSignInButton.setOnClickListener(v -> {
            viewModel.googleIsLoading.setValue(true);
            signInWithGoogle(googleSignIn);
        });

        viewModel.googleIsLoading.observe(this, isLoading -> {
            googleSignInButton.setText(isLoading ? "Loading ..." : "Sign in with Google");
            googleSignInButton.setEnabled(!isLoading);
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GOOGLE_SIGN_IN :
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                    viewModel.requestGoogleSignIn(googleSignInAccount);
                } catch (ApiException e) {
                    e.printStackTrace(); // update ui
                }
                break;
            case FACEBOOK_SIGN_IN: // TODO
            case TWITTER_SIGN_IN: // TODO
        }
    }


    private void signInWithGoogle(GoogleSignInClient googleSignInClient) {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, GOOGLE_SIGN_IN);
    }
}
