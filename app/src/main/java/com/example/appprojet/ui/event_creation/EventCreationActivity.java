package com.example.appprojet.ui.event_creation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojet.utils.ChildActivity;


public class EventCreationActivity extends ChildActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("Create a new new event");
    }

}
