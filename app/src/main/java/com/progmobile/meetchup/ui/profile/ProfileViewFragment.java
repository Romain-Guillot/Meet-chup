package com.progmobile.meetchup.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.ui.authentication.AuthenticationActivity;


/**
 * Fragment to display the user information :
 *  - profile picture
 *  - username
 *  - email address
 *
 * It simply retrieve the ProfileViewModel and set the user information in the corresponding views.
 * There is also a button to sign out.
 *
 * TODO : when user is not connected, display another view
 */
public class ProfileViewFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        ProfileViewModel viewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);

        TextView usernameView = view.findViewById(R.id.profile_view_user_name);
        TextView userEmailView = view.findViewById(R.id.profile_view_user_email);

        // button to sign out
        view.findViewById(R.id.profile_sign_out).setOnClickListener(v -> {
            viewModel.signOut();
            Intent authIntent = new Intent(getActivity(), AuthenticationActivity.class);
            authIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(authIntent);
        });

        // update UI profile information
        viewModel.user.observe(this, user -> {
            Log.e(">>>>>>>>>>>>>>>>>>", "OK");
            if (user != null) {
                String name = user.getName();
                usernameView.setText(name != null ? name : getString(R.string.default_username));
                userEmailView.setText(user.getEmail());
            }
        });

        return view;
    }


    /** Create the menu in the action bar with the edit item, see the activity for the process */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_view_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
