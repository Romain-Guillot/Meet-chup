package com.progmobile.meetchup.ui.event_creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.utils.form_views.DateFormLayout;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.FormFragment;
import com.progmobile.meetchup.utils.form_views.LocationFormLayout;
import com.progmobile.meetchup.utils.form_views.TextFormLayout;

import java.util.Arrays;


/**
 * <p>Holds the form, as others forms, it is a subclass of FromFragment {@link FormFragment}</p>
 *
 * <p>Here, we just bind the form's TextFormLayout {@link TextFormLayout} with the viewmodel's
 * FormDate {@link com.progmobile.meetchup.utils.form_data_with_validators.FormData}</p>
 */
public class EventCreationFragment extends FormFragment {

    private EventCreationViewModel viewModel;

    private DateFormLayout beginDateLayout;
    private DateFormLayout endDateLayout;
    private LocationFormLayout locationLayout;

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
        beginDateLayout = view.findViewById(R.id.event_creation_begindate);
        beginDateLayout.bindFormData(viewModel.beginDate);
        endDateLayout = view.findViewById(R.id.event_creation_enddate);
        endDateLayout.bindFormData(viewModel.endDate);
        locationLayout = view.findViewById(R.id.event_creation_location);
        locationLayout.bindFormData(viewModel.location);

        init(viewModel,
                Arrays.asList(titleLayout, descriptionLayout, beginDateLayout, endDateLayout, locationLayout),
                view.findViewById(R.id.event_creation_submit),
                getString(R.string.create_event_button),
                getString(R.string.loading_btn),
                getString(R.string.create_event_success));

        return view;
    }

    /**
     * We dismissed all dialog when the activity is in paused to prevent memory leaks
     */
    @Override
    public void onPause() {
        super.onPause();
        beginDateLayout.dismiss();
        endDateLayout.dismiss();
        locationLayout.dismiss();
    }
}
