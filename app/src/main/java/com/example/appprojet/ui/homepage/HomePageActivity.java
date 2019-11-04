package com.example.appprojet.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojet.R;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.ui.authentication.AuthenticationActivity;
import com.example.appprojet.ui.event_view.EventViewActivity;
import com.example.appprojet.ui.profile.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Handle the application homepage with :
 *      - The AppBar with the application name, the menu ;
 *      - The buttons to create or join an event ;
 *      - The user list of events.
 */
public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigateToAuthenticationActivityIfUserIsNotLogged();
        setContentView(R.layout.activity_homepage);
        setActionBar();


        Intent intent = new Intent(this, EventViewActivity.class);

        findViewById(R.id.event1).setOnClickListener(v -> {
            intent.putExtra(EventViewActivity.EXTRA_EVENT_ID, "1");
            startActivity(intent);
        });

        findViewById(R.id.event2).setOnClickListener(v -> {
            intent.putExtra(EventViewActivity.EXTRA_EVENT_ID, "2");
            startActivity(intent);
        });

    }

    public void navigateToAuthenticationActivityIfUserIsNotLogged() {
        IAuthenticationRepository authRepo = FirebaseAuthenticationRepository.getInstance();
        Log.e(">>>>>>>", (authRepo.getUser() == null ? "null" : "not null"));
        if (authRepo.getUser() == null) {
            Intent authActivityIntent =  new Intent(this, AuthenticationActivity.class);
            startActivity(authActivityIntent);
        }
    }

    /** Set the custom action bar title for the homepage */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return ;

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.view_homepage_title);
        View profileBtn = actionBar.getCustomView().findViewById(R.id.profile_button);
        profileBtn.setOnClickListener(v -> {
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);
        });
    }
}
