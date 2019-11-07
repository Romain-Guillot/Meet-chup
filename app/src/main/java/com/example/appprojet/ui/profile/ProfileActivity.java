package com.example.appprojet.ui.profile;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.ChildActivity;


public class ProfileActivity extends ChildActivity {

    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        viewModel.editMode.observe(this, this::setAppropriateFragment);
    }

    public void setAppropriateFragment(boolean isEditMode) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_main_fragment, isEditMode ? new ProfileEditFragment() : new ProfileViewFragment());
        if (isEditMode)
            trans.addToBackStack(null);
        trans.commit();

        setActionBarTitle(isEditMode ? "Edit profile" : "Your profile");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_edit_menu:
                viewModel.editMode.setValue(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
