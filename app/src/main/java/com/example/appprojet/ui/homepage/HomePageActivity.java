package com.example.appprojet.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojet.R;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.ui.authentication.AuthenticationActivity;
import com.example.appprojet.ui.event_view.EventViewActivity;
import com.example.appprojet.ui.homepage.invitation.JoinBottomSheetFragment;
import com.example.appprojet.ui.profile.ProfileActivity;


/**
 * Handle the application homepage with :
 *      - The AppBar with the application name, the menu ;
 *      - The buttons to create or join an event ;
 *      - The user list of events.
 */
public class HomePageActivity extends AppCompatActivity {

    private JoinBottomSheetFragment joinBottomSheetFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigateToAuthenticationActivityIfUserIsNotLogged();
        setContentView(R.layout.activity_homepage);

        initJoinBottomSheetBehavior();

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

    /** If I put the action bar in the onCreate the logo size will no be kept ...*/
    @Override
    protected void onResume() {
        super.onResume();
        setActionBar();
    }

    public void navigateToAuthenticationActivityIfUserIsNotLogged() {
        IAuthenticationRepository authRepo = FirebaseAuthenticationRepository.getInstance();
        if (authRepo.getCurrentUser() == null) {
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

    public void initJoinBottomSheetBehavior() {
        joinBottomSheetFragment = new JoinBottomSheetFragment();
        findViewById(R.id.homepage_join_event_btn).setOnClickListener(v -> {
            joinBottomSheetFragment.show(getSupportFragmentManager(), "join_bottom_sheet");
        });
    }
}
