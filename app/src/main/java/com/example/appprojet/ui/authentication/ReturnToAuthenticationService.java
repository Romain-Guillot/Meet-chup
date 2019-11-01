package com.example.appprojet.ui.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.utils.Callback;


/**
 * This service listen the user state provided by the [IAuthenticationRepository] implementation
 * of the app.
 *
 * When the user log off, the repository send a null user value to all listeners, and so, here we
 * register a listener to the authentication repository and when a null user value is returned
 * through the callback we start the authentication activity.
 *
 * For now, we remove all activities from the stack and we launch the authentication activity as it's
 * the authentication activity always start the homepage activity after the authentication process.
 * In fact, we should instead block the back navigation from the authentication activity and restore
 * the activity stack before the login
 */
public class ReturnToAuthenticationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent authActivityIntent = new Intent(this, AuthenticationActivity.class);
        authActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        authActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        FirebaseAuthenticationRepository.getInstance().addAuthStateListener(new Callback<User>() {
            @Override
            public void onSucceed(User result) {
                if (result == null)
                    startActivity(authActivityIntent);
            }

            @Override
            public void onFail(Exception e) { }
        });
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
