package com.progmobile.meetchup.utils;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.progmobile.meetchup.utils.form_views.FormLayout;

import java.util.List;


/**
 * Handle form UI, the business logic is handle by a FormViewModel associated.
 * <p>
 * To init the fragment call the init method with the required parameters. The method will make the
 * links between the form text fields and the fragment form data that holds the form
 * data.
 * <p>
 * It also initialize the listener on the submit button to notify the view model to process
 * the form. Finally, the init method observe the FormViewModel errorLive and isLoadingLive flags to
 * update to UI accordingly.
 * <p>
 * See the FormViewModel for more details about the form process.
 */
public abstract class FormFragment extends Fragment {

    /**
     * main function, refer to the class documentation
     * <p>
     * Required the following parameters :
     * - viewModel: the fragment view model
     * - the submit button
     * - textInputLayoutList: all form fields layouts
     * - formDataList: form data corresponding to the fields layout
     * i.e. formDataList[i] refers to the input text included in the layout textInputLayoutList[i]
     */
    protected void init(FormViewModel viewModel, List<FormLayout> layouts, Button submitButton, String submitText, String loadingText, String successMessage) {

        submitButton.setOnClickListener(v -> {
            for (FormLayout layout : layouts)
                layout.setLayoutError();
            viewModel.submitForm();
        });

        // update the submit button based on the loading form status
        viewModel.isLoadingLive.observe(this, isLoading -> {
            submitButton.setEnabled(!isLoading);
            submitButton.setText(isLoading ? loadingText : submitText);
        });

        // Show the error snackbar if  error message is sent
        viewModel.errorLive.observe(this, error -> {
            if (error != null) {
                String message = error.getContentIfNotHandled();
                SnackbarFactory.showErrorSnackbar(getActivity().findViewById(android.R.id.content), message);
            }
        });

        // Show the success snackbar if success
        viewModel.successLive.observe(this, success -> {
            if (successMessage != null && success.getContentIfNotHandled())
                SnackbarFactory.showSuccessSnackbar(getActivity().findViewById(android.R.id.content), successMessage);
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null)
            throw new RuntimeException("Unexpected fragment creation");
    }
}
