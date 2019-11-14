package com.example.appprojet.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;
import com.example.appprojet.ui.authentication.AuthenticationActivity;
import com.example.appprojet.ui.event_creation.EventCreationActivity;
import com.example.appprojet.ui.event_view.EventViewActivity;
import com.example.appprojet.ui.invitation.JoinBottomSheetFragment;
import com.example.appprojet.ui.profile.ProfileActivity;


/**
 * Handle the application homepage with :
 *      - The AppBar with the application name, the menu ;
 *      - The buttons to create or join an event ;
 *      - The user list of events.
 *      - The bottom sheet for joining an event
 */
public class HomePageActivity extends AppCompatActivity {

    private JoinBottomSheetFragment joinBottomSheetFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigateToAuthenticationActivityIfUserIsNotLogged();
        setContentView(R.layout.activity_homepage);

        // Event join bottom sheet fragment
        initJoinBottomSheetBehavior();

        // Create event button navigation
        findViewById(R.id.homepage_create_event_btn).setOnClickListener(v -> {
            Intent intent = new Intent(this, EventCreationActivity.class);
            startActivity(intent);
        });

        HomepageViewModel viewModel = ViewModelProviders.of(this).get(HomepageViewModel.class);
        viewModel.init(this);

        // -----------------------------------------------------------------------------------------
        // tests
        /*Intent intent = new Intent(this, EventViewActivity.class);

        findViewById(R.id.event1).setOnClickListener(v -> {
            intent.putExtra(EventViewActivity.EXTRA_EVENT_ID, "1");
            startActivity(intent);
        });

        findViewById(R.id.event2).setOnClickListener(v -> {
            intent.putExtra(EventViewActivity.EXTRA_EVENT_ID, "2");
            startActivity(intent);
        });*/
    }

    /** If I put the action bar in the onCreate the logo size will no be kept ...*/
    @Override
    protected void onResume() {
        super.onResume();
        setActionBar();
    }

    /** Check if the user is logged, go to the authentication activity if not */
    public void navigateToAuthenticationActivityIfUserIsNotLogged() {
        IAuthenticationRepository authRepo = FirebaseAuthenticationRepository.getInstance();
        if (authRepo.getCurrentUser() == null) {
            Intent authActivityIntent =  new Intent(this, AuthenticationActivity.class);
            startActivity(authActivityIntent);
        }
    }

    /** Show the bottom sheet for joining an event */
    public void initJoinBottomSheetBehavior() {
        joinBottomSheetFragment = new JoinBottomSheetFragment();
        findViewById(R.id.homepage_join_event_btn).setOnClickListener(v -> {
            joinBottomSheetFragment.show(getSupportFragmentManager(), "join_bottom_sheet");
        });
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
