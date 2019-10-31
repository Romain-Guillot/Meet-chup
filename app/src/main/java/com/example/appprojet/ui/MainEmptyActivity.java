package com.example.appprojet.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.ui.authentication.AuthenticationActivity;
import com.example.appprojet.ui.homepage.HomePageActivity;

/**
 * Inspired by this tutorial:
 *  title: Login and Main Activity Flow
 *  authors: Pierce Zaifman (Android Pub)
 *  link: https://android.jlelse.eu/login-and-main-activity-flow-a52b930f8351
 *
 *  It's simply a very simple activity to redirect the user to either the authentication activity or
 *  the homepage activity depending on the current authentication state.
 */
public class MainEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IAuthenticationRepository authRepo = FirebaseAuthenticationRepository.getInstance();
        Intent startupActivity;

        if (authRepo.getUser() == null)
            startupActivity = new Intent(this, AuthenticationActivity.class);
        else
            startupActivity = new Intent(this, HomePageActivity.class);

        startActivity(startupActivity);
        finish();
    }
}
