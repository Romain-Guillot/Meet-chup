package com.example.appprojet.ui.homepage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojet.R;


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
    }
}
