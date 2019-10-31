package com.example.appprojet.repositories;

import android.telecom.Call;

import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * API reference : https://firebase.google.com/docs/reference/android/com/google/firebase/auth/package-summary
 */
public class FirebaseAuthenticationRepository implements IAuthenticationRepository {

    private static FirebaseAuthenticationRepository INSTANCE = null;
    private FirebaseAuth firebaseAuth;
    private User user = null;


    private FirebaseAuthenticationRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        setUser();
        firebaseAuth.addAuthStateListener(state ->
            setUser()
        );

    }


    public static FirebaseAuthenticationRepository getInstance() {
        synchronized (FirebaseAuthenticationRepository.class) {
            if (INSTANCE == null)
                INSTANCE = new FirebaseAuthenticationRepository();
            return INSTANCE;
        }
    }


    @Override
    public User getUser() {
        return this.user;
    }


    @Override
    public void classicSignIn(String email, String password, Callback<User> callback) {

    }


    /**
     * Create a new user account with the given [email] and the [password].
     * If the account creation succeeds the user it also signs in the app and its corresponding
     * [User] instance is returned to the [callback.onSucceed] method.
     * If the account creation fails, the exception is returned to the [callback.onFail] method.
     *
     * Note : The user name is retrieved from his email address.
     * Ref : https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth.html#createUserWithEmailAndPassword(java.lang.String,%20java.lang.String)
     */
    @Override
    public void classicSignUp(String email, String password, Callback<User> callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            FirebaseUser fbUser = firebaseAuth.getCurrentUser();
            if (task.isSuccessful() && fbUser != null) {
                String temporaryName = getEmailAddressLocalPart(email);
                User user = new User(fbUser.getUid(), temporaryName, email);
                callback.onSucceed(user);
            } else {
                Exception exception = task.getException();
                callback.onFail(exception != null ? exception : new Exception());
            }
        });
    }

    /**
     * Ref : https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth.html#signOut()
     */
    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }



    private void setUser() {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser != null) {
            String email = fbUser.getEmail();
            if (email == null)
                email = "unknown";
            String temporaryName = getEmailAddressLocalPart(email);
            this.user = new User(fbUser.getUid(), email, temporaryName);
        } else {
            user = null;
        }
    }

    /**
     * @return the local part of an email address, the entire email string if the process has failed
     */
    private String getEmailAddressLocalPart(String email) {
        int index = email.indexOf('@');
        String localPart = email;
        if (index != -1)
            localPart = email.substring(0, index);
        return localPart;
    }
}
