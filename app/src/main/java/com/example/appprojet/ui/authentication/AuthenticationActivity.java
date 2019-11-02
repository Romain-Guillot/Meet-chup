package com.example.appprojet.ui.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.ui.authentication.set_up_profile.SetUpProfileFragment;
import com.example.appprojet.ui.authentication.sign_in_form.SignInFragment;
import com.example.appprojet.ui.authentication.sign_up_form.SignUpFragment;


/**
 * This activity holds fragments used to process the authentication in the app, including the
 * following authentication steps :
 *  - the sign in
 *  - the sign up
 *  - the profile information set up (for example the username)
 *
 * =================================================================================================
 *
 * When the authentication process is finished, the activity is destroyed (finish method) so the
 * the top activity of the stack is displayed. So, if there is no other activity in the stack, the
 * application will be destroyed after the authentication process.
 *
 * Basically, this application handle the navigation between the three main authentication fragments
 *  - SignInFragment
 *  - SignUpFragment
 *  - SetUpProfileFragment
 *
 * To achieve the navigation between the sign in, the sign up and the set up fragments, the current
 * form state is handle by an AuthenticationViewModel instance. This activity observe the current
 * form state and update the UI in accordingly.
 *
 * To change the form state between the sign in form and the sign up form, there is a button in
 * this activity to switch these forms. Of course, if the current form state is the user information
 * set up, this button is not displayed. When the user click on this button, the view model form
 * state is updated (and as the activity listen the current form state, the UI is also updated by
 * transitivity)
 *
 * To achieve the navigation between the sign in or the sign up form and the set up form, the
 * activity view model listen the authentication state thanks to the current application
 * IAuthenticationRepository. When the user is logged (and so the authentication repo notify its
 * listeners), depending on whether it is a new user, either the set up fragment is display or the
 * activity is destroyed (as the authentication process is finished).
 *
 * If the current form is the user information set up, then, the only way to finish this activity
 * is to retrieve the activity view model thanks to the obtainViewModel method, and to call the
 * finish method. This activity listen the finish flag boolean, and destroyed the activity when
 * it set to true.
 *
 * =================================================================================================
 *
 * Known bugs: none
 */
public class AuthenticationActivity extends AppCompatActivity {

    private AuthenticationViewModel viewModel;

    private View switchFormContainer;
    private TextView switchFormLabel;
    private Button switchFormBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        hideActionBar();

        viewModel = obtainViewModel(this);

        // init views that control the sign in / sign up forms switch
        switchFormContainer = findViewById(R.id.auth_switch_form_container);
        switchFormLabel = findViewById(R.id.auth_switch_form_label);
        switchFormBtn = findViewById(R.id.auth_switch_form_btn);

        // We update the main fragment and the activity views depending on the current form state
        viewModel.currentFormTypeLive.observe(this, formType -> {
            setAppropriateFragment(formType);
            updateUI(formType);
        });

        // We notify the view model that the user wants to switch forms
        switchFormBtn.setOnClickListener(v ->
            viewModel.switchSignInSignUpForm()
        );

        // we listen the finish flag and we destroyed the activity when this flags is set to true
        viewModel.isFinish.observe(this, b -> {
            if (b) {
                finish();
            }
        });
    }

    /** Replace the main activity fragment depending on the formType */
    private void setAppropriateFragment(AuthenticationViewModel.FormType formType) {
        Fragment fragment;
        switch (formType) {
            case SETUP:
                fragment = new SetUpProfileFragment();
                break;
            case SIGNUP:
                fragment = new SignUpFragment();
                break;
            case SIGNIN:
            default:
                fragment = new SignInFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_main_fragment_container, fragment)
                .commit();
    }

    /**
     * Update the activity views depending on the formType :
     *  - set up : switch controls are removed
     *  - sign in or sign up : update controls texts to switch forms
     */
    private void updateUI(AuthenticationViewModel.FormType formType) {
        switch (formType) {
            case SETUP:
                switchFormContainer.setVisibility(View.GONE);
                break;
            case SIGNUP:
                switchFormContainer.setVisibility(View.VISIBLE);
                switchFormLabel.setText(R.string.auth_switch_to_signin_label);
                switchFormBtn.setText(R.string.auth_switch_to_signin_btn);
                break;
            case SIGNIN:
            default:
                switchFormContainer.setVisibility(View.VISIBLE);
                switchFormLabel.setText(R.string.auth_switch_to_signup_label);
                switchFormBtn.setText(R.string.auth_switch_to_signup_btn);
        }
    }

    /** Return the activity view model */
    public static AuthenticationViewModel obtainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(AuthenticationViewModel.class);
    }

    /** hide the activity action bar, if any */
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }

    /** we override the back navigation behavior to disable it */
    @Override
    public void onBackPressed() { }
}
