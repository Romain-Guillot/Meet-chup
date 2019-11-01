package com.example.appprojet.ui.authentication;

import android.content.Intent;
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
import com.example.appprojet.ui.authentication.sign_auth.SignInFragment;
import com.example.appprojet.ui.authentication.sign_auth.SignUpFragment;
import com.example.appprojet.ui.homepage.HomePageActivity;


// TODO : here listen for authentication state changed and move in consequence
public class AuthenticationActivity extends AppCompatActivity {

    public AuthenticationViewModel viewModel;

    private View switchFormContainer;
    private TextView switchFormLabel;
    private Button switchFormBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        hideActionBar();

        viewModel = obtainViewModel(this);

        switchFormContainer = findViewById(R.id.auth_switch_form_container);
        switchFormLabel = findViewById(R.id.auth_switch_form_label);
        switchFormBtn = findViewById(R.id.auth_switch_form_btn);

        // There is already a data inside the LiveData currentFormTypeLive (initialisation value)
        // This form type will be delivered to us directly, no need for a specific initialization code
        viewModel.currentFormTypeLive.observe(this, formType -> {
            setAppropriateFragment(formType);
            updateUI(formType);
        });

        viewModel.moveToHomePage.observe(this, b -> {
            if (b) {
                startActivity(new Intent(this, HomePageActivity.class));
                finish();
            }
        });

        switchFormBtn.setOnClickListener(v ->
            viewModel.switchSignInSignUpForm()
        );
    }


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


    public static AuthenticationViewModel obtainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(AuthenticationViewModel.class);
    }


    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }


}
