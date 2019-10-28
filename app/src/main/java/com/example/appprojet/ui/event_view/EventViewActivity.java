package com.example.appprojet.ui.event_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.appprojet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * Required an intent with the event ID to load
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

        AppBarConfiguration navConfig = new AppBarConfiguration.Builder(
                R.id.event_view_navigation_feed,
                R.id.event_view_navigation_album,
                R.id.event_view_navigation_todo
        ).build();

        NavController navController = Navigation.findNavController(this, R.id.event_view_host_fragment);
        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.event_view_navigation);

//        graph.addArgument("event_id", new NavArgument.Builder().setDefaultValue(event_id).build());
        navController.setGraph(graph);

        NavigationUI.setupWithNavController(navView, navController);

        setBackPressActionBar();



//        navController.addOnDestinationChangedListener(
//            (controller, destination, arguments) -> {
//                NavArgument argument = new NavArgument.Builder().setDefaultValue(event_id).build();
//                destination.addArgument("event_id", argument);
//            }
//        );
    }

    private void setBackPressActionBar() {
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
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
