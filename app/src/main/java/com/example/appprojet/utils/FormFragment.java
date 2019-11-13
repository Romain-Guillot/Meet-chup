package com.example.appprojet.utils;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appprojet.R;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_views.FormLayout;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Handle form UI, the business logic is handle by a FormViewModel associated.
 *
 * To init the fragment call the init method with the required parameters. The method will make the
 * links between the form text fields and the fragment form data that holds the form
 * data.
 *
 * It also initialize the listener on the submit button to notify the view model to process
 * the form. Finally, the init method observe the FormViewModel errorLive and isLoadingLive flags to
 * update to UI accordingly.
 *
 * See the FormViewModel for more details about the form process.
 */
public abstract class FormFragment extends Fragment {

    /**
     * main function, refer to the class documentation
     *
     * Required the following parameters :
     *  - viewModel: the fragment view model
     *  - the submit button
     *  - textInputLayoutList: all form fields layouts
     *  - formDataList: form data corresponding to the fields layout
     *      i.e. formDataList[i] refers to the input text included in the layout textInputLayoutList[i]
     */
    protected void init(FormViewModel viewModel, Button submitButton, String submitText, String loadingText, String successMessage) {

        submitButton.setOnClickListener(v -> {
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
