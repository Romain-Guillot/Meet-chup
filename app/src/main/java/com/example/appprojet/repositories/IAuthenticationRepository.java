package com.example.appprojet.repositories;

import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface IAuthenticationRepository {

    User getUser();

    void classicSignIn(String email, String password, Callback<User> callback);

    void googleSignIn(GoogleSignInAccount googleSignInAccount, Callback<User> callback);


    /**
     * Create a new user account with the given [email] and the [password].
     * If the account creation succeeds the user it also signs in the app and its corresponding
     * [User] instance is returned to the [callback.onSucceed] method.
     * If the account creation fails, the exception is returned to the [callback.onFail] method.
     */
    void classicSignUp(String email, String password, Callback<User> callback);

    void signOut();



}
