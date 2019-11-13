package com.progmobile.meetchup.ui.invitation;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.SnackbarFactory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

/**
 * This fragment holds the bottom sheet fragment that displays the text field to join an event with
 * and invitation key.
 *
 * When the view is created, the fragment show the keyboard (and hide when the view is destroy)
 *
 * The process of the form is handled by the view model {@link JoinBottomSheetViewModel}
 *
 * Here we just update the submit button text according the the viewmodel live data.
 *
 * NOTE :   this fragment behavior is the FormFragment behavior {@link com.progmobile.meetchup.utils.FormFragment}
 *          but as the multiple inheritance is not possible, I cannot extends this fragment of the FormFragment sadly
 *          Maybe transform the inheritance to association ...
 */
public class JoinBottomSheetFragment extends BottomSheetDialogFragment {

    private JoinBottomSheetViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(JoinBottomSheetViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showKeyboard();
        View view = inflater.inflate(R.layout.fragment_join_event, container, false);

        // Views init
        TextInputLayout invitKeyTextLayout = view.findViewById(R.id.join_event_key_layout);
        EditText editText = invitKeyTextLayout.getEditText();
        editText.requestFocus();
        Button submitButton = view.findViewById(R.id.join_event_btn);

        // Update the view model when the user change the input field
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.keyFormData = s.toString();
            }
        });

        // Request the view model to submit the form
        submitButton.setOnClickListener(v ->
                viewModel.submitForm()
        );

        // Update button is the submission is in progress
        viewModel.isLoadingLive.observe(this, isLoading -> {
            submitButton.setEnabled(!isLoading);
            submitButton.setText(isLoading ? R.string.loading_btn : R.string.join_event_btn);
        });

        // Show the error, if any
        viewModel.errorLive.observe(this, error -> {
            String message = error.getContentIfNotHandled();
            if (message != null)
                SnackbarFactory.showTopErrorSnackbar(getActivity().findViewById(R.id.container), message);
        });

        // Show the success message, if any
        viewModel.successLive.observe(this, success -> {
            if (success.getContentIfNotHandled() != null) {
                SnackbarFactory.showTopSuccessSnackbar(getActivity().findViewById(R.id.container), "Event added !");
                dismiss();
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeKeyboard();
    }


    private void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    private void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
