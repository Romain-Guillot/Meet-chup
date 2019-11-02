package com.example.appprojet.ui.authentication.providers_auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.Callback;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class ProvidersAuthenticationViewModel extends ViewModel {

    private final IAuthenticationRepository authenticationRepository;

    final MutableLiveData<Boolean> googleIsLoading = new MutableLiveData<>(false);

    public ProvidersAuthenticationViewModel() {
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
    }


    void requestGoogleSignIn(GoogleSignInAccount googleSignInAccount) {
        if (googleSignInAccount == null)
            throw new RuntimeException("Cannot request a connexion with a null Google account");

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        authenticationRepository.credentialSignIn(authCredential, new Callback<User>() {
            @Override
            public void onSucceed(User result) {
                googleIsLoading.setValue(false);
            }

            @Override
            public void onFail(Exception e) {
                googleIsLoading.setValue(false);
            }
        });

    }

}
