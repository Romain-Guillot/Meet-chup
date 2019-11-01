package com.example.appprojet.ui.authentication.set_up_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.ui.authentication.AuthenticationActivity;
import com.example.appprojet.ui.authentication.FormFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;

public class SetUpProfileFragment extends FormFragment {

    SetUpProfileViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_setup, container, false);

        TextInputLayout nameLayout = view.findViewById(R.id.auth_setup_name_layout);

        viewModel = ViewModelProviders.of(this).get(SetUpProfileViewModel.class);

        init(
                view,
                viewModel,
                Collections.singletonList(nameLayout),
                Collections.singletonList(viewModel.nameLive)
        );

        viewModel.isFinish.observe(this, isFinish -> {
            if (isFinish) AuthenticationActivity.obtainViewModel(getActivity()).finish();
        });


        return view;
    }
}
