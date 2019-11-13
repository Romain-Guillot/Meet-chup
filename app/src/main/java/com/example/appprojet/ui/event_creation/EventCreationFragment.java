package com.example.appprojet.ui.event_creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.FormFragment;
import com.example.appprojet.utils.form_views.FormLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Array;
import java.util.Arrays;

public class EventCreationFragment extends FormFragment {

    private EventCreationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null)
            throw new RuntimeException("Invalid fragment creation");
        viewModel = ViewModelProviders.of(getActivity()).get(EventCreationViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_creation_form, container, false);

        ((FormLayout) view.findViewById(R.id.event_creation_title))
                .bindFormData(viewModel.titleField);
        ((FormLayout) view.findViewById(R.id.event_creation_description))
                .bindFormData(viewModel.descriptionField);
        ((FormLayout) view.findViewById(R.id.event_creation_begindate))
                .bindFormData(viewModel.beginDate);
        ((FormLayout) view.findViewById(R.id.event_creation_enddate))
                .bindFormData(viewModel.endDate);

        init(viewModel, view.findViewById(R.id.event_creation_submit), "Create", "Loading ...", "Event created");

        return view;
    }
}
