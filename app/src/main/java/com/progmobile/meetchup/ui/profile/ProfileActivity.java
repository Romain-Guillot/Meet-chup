package com.progmobile.meetchup.ui.profile;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.ChildActivity;

/**
 * <h1>ProfileActivity</h1>
 *
 * <p>This activity holds fragment to :</p>
 * <ul>
 *     <li>display user profile {@link ProfileViewFragment}</li>
 *     <li>edit user profile {@link ProfileEditFragment}</li>
 * </ul>
 *
 * <p>The activity simple retrieve it ViewModel ({@link ProfileEditViewModel}) and observe when to
 * change between the view mode and the edit mode.
 *
 * <p>The edit mode is displayed when the user click on the action nav bar item "edit"<br>
 * The view mode is displayed by default OR if the user back navigate from the edit mode</p>
 *
 *
 * <h2>INTENT COMMUNICATION</h2>
 * No extra data required
 *
 *
 * <h2>BUGS</h2>
 * <p>None</p>
 */
public class ProfileActivity extends ChildActivity {

    private ProfileViewModel viewModel;

    /**
     * Retrieve the view model and observe the form mode modifications
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        viewModel.editMode.observe(this, this::setAppropriateFragment);
    }


    /**
     * Set the appropriate fragment according if we are in edit mode or not
     */
    private void setAppropriateFragment(boolean isEditMode) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_main_fragment, isEditMode ? new ProfileEditFragment() : new ProfileViewFragment());
        trans.commit();

        setActionBarTitle(isEditMode ? "Edit profile" : "Your profile");
    }


    /**
     * Set the edit mode if the user click on the edit item
     */
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


    /**
     * override back navigation to navigate with our fragments
     */
    @Override
    public void onBackPressed() {
        if (viewModel != null && viewModel.editMode.getValue() != null && viewModel.editMode.getValue())
            viewModel.editMode.setValue(false);
        else
            super.onBackPressed();
    }
}
