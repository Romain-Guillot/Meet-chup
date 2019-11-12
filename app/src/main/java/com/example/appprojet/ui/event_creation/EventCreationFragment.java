package com.example.appprojet.ui.event_creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.utils.FormFragment;

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

//        init(
//                viewModel,
//                null,
//                null,
//                view.findViewById(R.id.event_creation_submit),
//                "Create",
//                "Loading ...",
//                "Event created"
//        );

        return view;
    }
}
