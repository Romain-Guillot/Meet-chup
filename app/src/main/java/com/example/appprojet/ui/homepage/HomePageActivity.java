package com.example.appprojet.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojet.R;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.ui.authentication.AuthenticationActivity;
import com.example.appprojet.ui.event_view.EventViewActivity;
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

        IAuthenticationRepository authRepo = FirebaseAuthenticationRepository.getInstance();
        Intent startupActivity;

        if (authRepo.getUser() == null)
            startupActivity = new Intent(this, AuthenticationActivity.class);
        else
            startupActivity = new Intent(this, HomePageActivity.class);

        startActivity(startupActivity);

        setContentView(R.layout.activity_homepage);

        Intent intent = new Intent(this, EventViewActivity.class);

        findViewById(R.id.event1).setOnClickListener(v -> {
            intent.putExtra(EventViewActivity.EXTRA_EVENT_ID, "1");
            startActivity(intent);
        });

        Button b = findViewById(R.id.event2);
        b.setText(FirebaseAuthenticationRepository.getInstance().getUser().getName());
        b.setOnClickListener(v -> {
            FirebaseAuthenticationRepository.getInstance().signOut();
        });




    }
}
