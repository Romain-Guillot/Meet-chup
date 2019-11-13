package com.progmobile.meetchup.ui.post_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.R;


public class PostViewActivity extends AppCompatActivity {

    public static final String EXTRA_POST_ID = "post_id";

    private PostViewViewModel viewModel;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        this.actionBar = getSupportActionBar();

        Intent intent = getIntent();
        String post_id = intent.getStringExtra(EXTRA_POST_ID);

        if (post_id == null)
            throw new RuntimeException();

        viewModel = ViewModelProviders.of(this).get(PostViewViewModel.class);
        viewModel.initPost(post_id);

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
