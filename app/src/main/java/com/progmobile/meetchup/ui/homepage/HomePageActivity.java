package com.progmobile.meetchup.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.repositories.FirebaseAuthenticationRepository;
import com.progmobile.meetchup.repositories.IAuthenticationRepository;
import com.progmobile.meetchup.ui.authentication.AuthenticationActivity;
import com.progmobile.meetchup.ui.event_creation.EventCreationActivity;
import com.progmobile.meetchup.ui.invitation.JoinBottomSheetFragment;
import com.progmobile.meetchup.ui.profile.ProfileActivity;


/**
 * Handle the application homepage with :
 * - The AppBar with the application name, the menu ;
 * - The buttons to create or join an event ;
 * - The user list of events.
 * - The bottom sheet for joining an event
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        HomepageViewModel viewModel = ViewModelProviders.of(this).get(HomepageViewModel.class);
        viewModel.init(this);
    }

    /**
     * If I put the action bar in the onCreate the logo size will no be kept ...
     */
    @Override
    protected void onResume() {
        super.onResume();
        setActionBar();
    }

    /**
     * Check if the user is logged, go to the authentication activity if not
     */
    public void navigateToAuthenticationActivityIfUserIsNotLogged() {
        IAuthenticationRepository authRepo = FirebaseAuthenticationRepository.getInstance();
        if (authRepo.getCurrentUser() == null) {
            Intent authActivityIntent = new Intent(this, AuthenticationActivity.class);
            startActivity(authActivityIntent);
        }
    }

    /**
     * Show the bottom sheet for joining an event
     */
    public void initJoinBottomSheetBehavior() {
        joinBottomSheetFragment = new JoinBottomSheetFragment();
        findViewById(R.id.homepage_join_event_btn).setOnClickListener(v -> {
            joinBottomSheetFragment.show(getSupportFragmentManager(), "join_bottom_sheet");
        });
    }

    /**
     * Set the custom action bar title for the homepage
     */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.view_homepage_title);
        View profileBtn = actionBar.getCustomView().findViewById(R.id.profile_button);
        profileBtn.setOnClickListener(v -> {
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);
        });
    }
}
