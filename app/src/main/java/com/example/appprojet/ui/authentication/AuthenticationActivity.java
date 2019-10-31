package com.example.appprojet.ui.authentication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;

public class AuthenticationActivity extends AppCompatActivity {

    public AuthenticationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        viewModel = ViewModelProviders.of(this).get(AuthenticationViewModel.class);
        setAppropriateFragment(viewModel.currentFormType);

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

    private void disableFormSwitchView() {

    }
}
