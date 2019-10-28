package com.example.appprojet.ui.event_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.appprojet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * [EventViewActivity] handles the event visualisation throw the feed, the album and the to-do list.
 * [EventViewActivity] requires an extra string in the intend to load the appropriate event.
 *
 * ## INTENT COMMUNICATION
 * The intent extra string is the event ID and the extra name has to be [EXTRA_EVENT_ID].
 *
 *
 * ## ARCHITECTURE
 * This activity can contains three fragments :
 *
 * - FeedFragment
 * - AlbumFragment
 * - ToDoFragment
 *
 * The entire business logic for these fragments and this activity is handled by the
 * [EventViewViewModel]
 *
 *
 * ## PURPOSE
 * This class handle the event view. So the activity displays a BottomNavigationBar with the
 * following three tabs :
 *
 * - Feed
 * - Album
 * - ToDoList
 *
 * Each tab corresponds to one fragment. When the user click on one tab, the associated fragment
 * is inflate in the activity layout. The navigation system is handled with :
 * - The BottomNavigationView view which defines the menu corresponding to the 3 tab (see activity layout)
 * - The menu previously mentioned menu (see event_view_bottom_nav_menu.xml menu file)
 * - The fragment view which defines the navigation graph (see activity layout)
 * - The previously mentioned navigation graph (see event_view_navigation.xml navigation file)
 * - The navigation controller defined in this class
 *
 * The last thing this activity does is to set the action bar title according to the event name.
 * The title of the event is provided by the ViewModel associated with this activity. See
 * [EventViewViewModel]
 *
 * TODO 1: Add loading text until the title is available
 * TODO 2: Handle event loading error (wrong id for example)
 * TODO 3: Delegate ViewModel responsibilities to three sub-viewmodels for each fragment
 */
public class EventViewActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT_ID = "com.example.appprojet.event_id";

    private ActionBar actionBar;

    EventViewViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        this.actionBar = getSupportActionBar();

        Intent intent = getIntent();
        String event_id = intent.getStringExtra(EXTRA_EVENT_ID);

        viewModel = ViewModelProviders.of(this).get(EventViewViewModel.class);
        viewModel.initEventMetaData(event_id);

        viewModel.eventTitleLive.observe(this, event -> {
                if (actionBar != null)
                    actionBar.setTitle(event);
        });

        BottomNavigationView navView = findViewById(R.id.event_nav_view);
        NavController navController = Navigation.findNavController(this, R.id.event_view_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        setBackPressActionBar();
    }


    private void setBackPressActionBar() {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
