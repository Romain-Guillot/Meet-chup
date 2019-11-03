package com.example.appprojet.ui.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.utils.Callback;


/**
 * Service to listen the user state provided by the [IAuthenticationRepository] implementation
 * of the app.
 *
 * When the user sign out, the repository send a null user value to all listeners, and so, here we
 * register a listener to the authentication repository and when a null user value is returned
 * through the callback we start the authentication activity.
 *
 * This service must only be started once (typically when the application is launch -> see the
 * MeetChupApplication class)
 */
public class ReturnToAuthenticationService extends Service {

    private IAuthenticationRepository authenticationRepository;
    private Intent authActivityIntent;
    private final Callback<User> userAuthStateCallback = new Callback<User>() {
        @Override public void onFail(Exception e) { }
        @Override
        public void onSucceed(User result) {
            if (result == null)
                startActivity(authActivityIntent);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(">>>>>>>>>>", "SERVICE START");
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();

        authActivityIntent =  new Intent(this, AuthenticationActivity.class);
        authActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        authenticationRepository.addAuthStateListener(userAuthStateCallback);

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(">>>>>>>>>>>>>>", "SERVICE DESTROY");
        authenticationRepository.removeAuthStateListener(userAuthStateCallback);
    }
}
