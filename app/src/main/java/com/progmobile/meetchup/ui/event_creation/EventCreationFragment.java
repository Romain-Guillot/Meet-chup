package com.progmobile.meetchup.ui.event_creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;


import com.progmobile.meetchup.utils.form_views.DateFormLayout;
import com.progmobile.meetchup.utils.form_views.FormLayout;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.FormFragment;
import com.progmobile.meetchup.utils.form_views.TextFormLayout;

import java.util.Arrays;
import java.util.List;


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

        TextFormLayout titleLayout = view.findViewById(R.id.event_creation_title);
        titleLayout.bindFormData(viewModel.titleField);
        TextFormLayout descriptionLayout = view.findViewById(R.id.event_creation_description);
        descriptionLayout.bindFormData(viewModel.descriptionField);
        DateFormLayout beginDateLayout = view.findViewById(R.id.event_creation_begindate);
        beginDateLayout.bindFormData(viewModel.beginDate);
        DateFormLayout endDateLayout = view.findViewById(R.id.event_creation_enddate);
        endDateLayout.bindFormData(viewModel.endDate);

        init(viewModel,
                Arrays.asList(titleLayout, descriptionLayout, beginDateLayout, endDateLayout),
                view.findViewById(R.id.event_creation_submit),
                "Create",
                "Loading ...",
                "Event created");

        return view;
    }
}
