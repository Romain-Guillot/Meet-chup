package com.example.appprojet.repositories;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appprojet.models.User;
import com.example.appprojet.utils.Callback;
import com.example.appprojet.utils.CallbackException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


/**
 * API reference : https://firebase.google.com/docs/reference/android/com/google/firebase/auth/package-summary
 */
public class FirebaseAuthenticationRepository implements IAuthenticationRepository {

    private static FirebaseAuthenticationRepository INSTANCE = null;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private MutableLiveData<User> user;


    private FirebaseAuthenticationRepository() {
        user = new MutableLiveData<>();
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        setUser();
    }


    public static FirebaseAuthenticationRepository getInstance() {
        synchronized (FirebaseAuthenticationRepository.class) {
            if (INSTANCE == null)
                INSTANCE = new FirebaseAuthenticationRepository();
            return INSTANCE;
        }
    }


    @Override
    public User getCurrentUser() {
        return user.getValue();
    }

    @Override
    public LiveData<User> getObservableUser() {
        return user;
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
    public void updateEmail(String email, Callback<User> callback) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED));
            return ;
        }

        fbUser.updateEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User _user = getCurrentUser();
                _user.setEmail(email);
                this.user.setValue(_user);
                callback.onSucceed(_user);
            } else {
                callback.onFail(CallbackException.fromFirebaseException(task.getException()));
            }
        });
    }

    @Override
    public void updatePassword(String newPassword, Callback<User> callback) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED));
            return ;
        }
        fbUser.updatePassword(newPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSucceed(getCurrentUser());
            } else {
                callback.onFail(CallbackException.fromFirebaseException(task.getException()));
            }
        });
    }


    @Override
    public void updateName(String name, Callback<User> callback) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();

        if (fbUser != null) {
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("name", name);
            getUserDocument(fbUser.getUid()).set(userInfo, SetOptions.merge()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User _user = getCurrentUser();
                    _user.setName(name);
                    user.setValue(_user);
                    callback.onSucceed(_user);
                } else {
                    callback.onFail(CallbackException.fromFirebaseException(task.getException()));
                }
            });
        } else {
            callback.onFail(new CallbackException());
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

    private void setUser() {
        setUser(false);
    }

    private void setUser(boolean firstLogin) {
        final FirebaseUser fbUser = firebaseAuth.getCurrentUser();

        if (fbUser != null) {
            final String email = fbUser.getEmail() != null ? fbUser.getEmail() : "unknown";
            user.setValue(new User(fbUser.getUid(), null, email, firstLogin));

            getUserDocument(fbUser.getUid()).get().addOnCompleteListener(task -> {
                String name = null;
                try {
                    if (task.isSuccessful()) {
                        DocumentSnapshot userInfoDoc = task.getResult();
                        if (userInfoDoc.exists()) {
                            name = (String) userInfoDoc.getData().get("name");
                        }
                    }
                } catch (Exception e) { }
                user.setValue(new User(fbUser.getUid(), name, email, firstLogin));
            });
        } else {
            this.user.setValue(null);
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
                callback.onSucceed(user.getValue());
            } else {
                callback.onFail(CallbackException.fromFirebaseException(task.getException()));
            }
        }
    }

    private DocumentReference getUserDocument(String uid) {
        return firestore.collection("users").document(uid);
    }
}
