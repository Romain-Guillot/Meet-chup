package com.progmobile.meetchup.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.ui.authentication.set_up_profile.SetUpProfileFragment;
import com.progmobile.meetchup.ui.authentication.sign_in_form.SignInFragment;
import com.progmobile.meetchup.ui.authentication.sign_up_form.SignUpFragment;
import com.progmobile.meetchup.ui.homepage.HomePageActivity;


/**
 * This activity holds fragments used to process the authentication in the app, including the
 * following authentication steps :
 * - the sign in
 * - the sign up
 * - the profile information set up (for example the username)
 * <p>
 * =================================================================================================
 * <p>
 * When the authentication process finished, the activity is destroyed (finish method) so the
 * the top activity of the stack is displayed. So, if there is no other activity in the stack, the
 * application will be destroyed after the authentication process.
 * <p>
 * Basically, this application handle the navigation between the three main authentication fragments
 * - SignInFragment (that contains a ProvidersAuthFragment)
 * - SignUpFragment (that contains a ProvidersAuthFragment)
 * - SetUpProfileFragment
 * <p>
 * To achieve the navigation between the sign in, the sign up and the set up fragments, the current
 * form state is handled by an AuthenticationViewModel instance. This activity observes the current
 * form state and update the UI in accordingly.
 * <p>
 * To change the form state between the sign in form and the sign up form, there is a button in
 * this activity to switch these forms. Of course, if the current form state is the user information
 * set up, this button is not displayed. When the user click on this button, the view model form
 * state is updated (and as the activity listen the current form state, the UI is also updated by
 * transitivity)
 * <p>
 * To achieve the navigation between the sign in or the sign up form and the set up form, the
 * activity view model listen the authentication state thanks to the current application
 * IAuthenticationRepository. When the user is logged in (and so the authentication repo notify its
 * listeners), depending on whether it is a new user, either the set up fragment is display or the
 * activity is destroyed (as the authentication process is finished).
 * <p>
 * If the current form is the user information set up, then, the only way to finish this activity
 * is to retrieve the activity view model thanks to the obtainViewModel method, and to call the
 * finish method. This activity listen the isFinish flag boolean, and destroyed the activity when
 * it set to true.
 * <p>
 * =================================================================================================
 * <p>
 * Known bugs: none
 */
public class AuthenticationActivity extends AppCompatActivity {

    private AuthenticationViewModel viewModel;

    private View switchFormContainer;
    private TextView switchFormLabel;
    private Button switchFormBtn;

    /**
     * Return the activity view model
     */
    public static AuthenticationViewModel obtainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(AuthenticationViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

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
                startActivity(new Intent(this, HomePageActivity.class));
                finish();
            }
        });

        // *   - if it's a new user (first connexion) -> we update the current form state to the set up form
        //  *   - else -> we call the finish method to end the process
        viewModel.userLive.observe(this, user -> {
            if (user != null) {
                if (user.isFirstLogIn())
                    viewModel.currentFormTypeLive.setValue(AuthenticationViewModel.FormType.SETUP);
                else viewModel.finish();
            }
        });
    }

    /**
     * Replace the main activity fragment depending on the formType
     */
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
     * - set up : switch controls are removed
     * - sign in or sign up : update controls texts to switch forms
     */
    private void updateUI(AuthenticationViewModel.FormType formType) {
        switch (formType) {
            case SETUP:
                switchFormContainer.setVisibility(View.GONE);
                break;
            case SIGNUP:
                switchFormContainer.setVisibility(View.VISIBLE);
                switchFormLabel.setText(R.string.auth_switch_to_signin_label);
                switchFormBtn.setText(R.string.auth_signin_btn);
                break;
            case SIGNIN:
            default:
                switchFormContainer.setVisibility(View.VISIBLE);
                switchFormLabel.setText(R.string.auth_switch_to_signup_label);
                switchFormBtn.setText(R.string.auth_signup_btn);
        }
    }

    /**
     * we override the back navigation
     * - we disable it if the authentication process is not finished
     * - if it's not the root activity, we go to the previous activity
     * - else, we go to the homepage
     */
    @Override
    public void onBackPressed() {
        if (viewModel.isFinish.getValue() != null && viewModel.isFinish.getValue()) {
            if (!isTaskRoot())
                super.onBackPressed();
            else
                startActivity(new Intent(this, HomePageActivity.class));
        }
    }
}
