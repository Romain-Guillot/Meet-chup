package com.example.appprojet.repositories;

import androidx.lifecycle.LiveData;

import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;
import com.google.firebase.auth.AuthCredential;


/**
 *
 */
public interface IAuthenticationRepository {

    int NAME_MIN_LENGTH = 6;
    int NAME_MAX_LENGTH = 15;

    LiveData<User> getObservableUser();

    User getCurrentUser();


    void classicSignIn(String email, String password, Callback<User> callback);

    void credentialSignIn(AuthCredential authCredential, Callback<User> callback);


    /**
     * Create a new user account with the given [email] and the [password].
     * If the account creation succeeds the user it also signs in the app and its corresponding
     * [User] instance is returned to the [callback.onSucceed] method.
     * If the account creation fails, the exception is returned to the [callback.onFail] method.
     */
    void classicSignUp(String email, String password, Callback<User> callback);

    void updateEmail(String email, Callback<User> callback);

    void updatePassword(String newPassword, Callback<User> callback);

    void updateName(String name, Callback<User> callback);


    void signOut();

    void deleteAccount(Callback<Void> callback);
}
