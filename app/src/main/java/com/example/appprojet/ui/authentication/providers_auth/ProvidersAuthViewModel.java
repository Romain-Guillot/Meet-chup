package com.example.appprojet.ui.authentication.providers_auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;


/**
 * This viewmodel contains the different functions to call to connect via the following providers:
 *  Google, Facebook and Twitter.
 *
 * For each provider, there is an associated loading flag set to true for the duration of the
 * connection processing by the authentication repository.
 */
public class ProvidersAuthViewModel extends ViewModel {

    private final IAuthenticationRepository authenticationRepository;

    final MutableLiveData<Boolean> googleIsLoading = new MutableLiveData<>(false);
//    final MutableLiveData<Boolean> twitterIsLoading = new MutableLiveData<>(false);
//    final MutableLiveData<Boolean> facebookIsLoading = new MutableLiveData<>(false);


    public ProvidersAuthViewModel() {
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
    }

    /** Create the AuthCredential from the GoogleSignInAccount parameter and request the connexion to the repo */
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
            public void onFail(CallbackException e) {
                googleIsLoading.setValue(false);
            }
        });
    }

//    void requestTwitterSignIn() {}

//    void requestFacebookSignIn() {}

}
