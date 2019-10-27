package com.example.appprojet.ui.event_view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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

    EventViewViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        Intent intent = getIntent();
        String event_id = intent.getStringExtra(EXTRA_EVENT_ID);

        viewModel = ViewModelProviders.of(this).get(EventViewViewModel.class);
        viewModel.initEvent(event_id);

        viewModel.eventLiveData.observe(this, event -> {
            if (event.getTitle() != null) {
//                getActionBar().setTitle(event.getTitle());
                getSupportActionBar().setTitle(event.getTitle());
            }
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

        NavigationUI.setupActionBarWithNavController(this, navController, navConfig);
        NavigationUI.setupWithNavController(navView, navController);



//        navController.addOnDestinationChangedListener(
//            (controller, destination, arguments) -> {
//                NavArgument argument = new NavArgument.Builder().setDefaultValue(event_id).build();
//                destination.addArgument("event_id", argument);
//            }
//        );
    }


}
