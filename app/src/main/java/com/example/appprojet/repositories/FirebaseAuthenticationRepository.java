package com.example.appprojet.repositories;


import androidx.annotation.NonNull;

import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.List;



/**
 * API reference : https://firebase.google.com/docs/reference/android/com/google/firebase/auth/package-summary
 */
public class FirebaseAuthenticationRepository implements IAuthenticationRepository {

    private static FirebaseAuthenticationRepository INSTANCE = null;

    private FirebaseAuth firebaseAuth;

    private User user;
    private List<Callback<User>> authStateListeners;


    private FirebaseAuthenticationRepository() {
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListeners = new ArrayList<>();
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
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnSignComplete(callback));
    }


    @Override
    public void credentialSignIn(AuthCredential authCredential, Callback<User> callback) {
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnSignComplete(callback));
    }

    /**
     * Create a new user account with the given [email] and the [password].
     * If the account creation succeeds the user it also signs in the app and its corresponding
     * [User] instance is returned to the [callback.onSucceed] method.
     * If the account creation fails, the exception is returned to the [callback.onFail] method.
     * <p>
     * Note : The user name is retrieved from his email address.
     * Ref : https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth.html#createUserWithEmailAndPassword(java.lang.String,%20java.lang.String)
     */
    @Override
    public void classicSignUp(String email, String password, Callback<User> callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnSignComplete(callback));
    }

    @Override
    public void updateName(String name, Callback<User> callback) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();

        if (fbUser != null) {
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build();
            fbUser.updateProfile(profileUpdate).addOnCompleteListener(task -> {
               if (task.isSuccessful()) callback.onSucceed(user);
               else callback.onFail(task.getException());
            });
        } else {
            callback.onFail(new Exception(""));
        }
    }

    /**
     * Ref : https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth.html#signOut()
     */
    @Override
    public void signOut() {
        firebaseAuth.signOut();
        setUser();
    }


    @Override
    public void addAuthStateListener(Callback<User> listener) {
        this.authStateListeners.add(listener);
    }


    @Override
    public void removeAuthStateListener(Callback<User> listener) {
        this.authStateListeners.remove(listener);
    }

    private void setUser() {
        setUser(false);
    }

    private void setUser(boolean firstLogin) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();

        if (fbUser != null) {
            String email = fbUser.getEmail();
            if (email == null) email = "unknown";

            String name = fbUser.getDisplayName();
            if (name == null) name = getEmailAddressLocalPart(email);

            user = new User(fbUser.getUid(), name, email, firstLogin);
        } else {
            this.user = null;
        }

        for (Callback<User> listener : authStateListeners)
            listener.onSucceed(user);
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


    private class OnSignComplete implements OnCompleteListener<AuthResult> {

        Callback<User> callback;

        OnSignComplete(Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                boolean firstLogin = task.getResult().getAdditionalUserInfo().isNewUser();
                setUser(firstLogin);
                callback.onSucceed(user);
            } else {
                Exception exception = task.getException();
                callback.onFail(exception != null ? exception : new Exception());
            }
        }
    }
}
