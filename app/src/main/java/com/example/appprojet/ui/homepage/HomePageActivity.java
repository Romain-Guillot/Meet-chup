package com.example.appprojet.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojet.R;
import com.example.appprojet.ui.news_feed_view.NewsFeedActivity;
import com.example.appprojet.ui.post_creation.PostCreationActivity;
import com.example.appprojet.ui.post_creation.PostCreationFragment;


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
        setContentView(R.layout.activity_homepage);

        Intent intent = new Intent(HomePageActivity.this, PostCreationActivity.class);
        startActivity(intent);
    }
}
