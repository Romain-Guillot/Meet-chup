package com.example.appprojet;

import android.app.Application;
import android.content.Intent;

import com.example.appprojet.ui.authentication.ReturnToAuthenticationService;

public class MeetChupApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // We start a service when the app is launched to redirect the user to the authentication
        // page when he signs out
        Intent authService = new Intent(getApplicationContext(), ReturnToAuthenticationService.class);
        startService(authService);
    }
}
