package com.example.appprojet.ui.authentication;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.appprojet.R;
import com.example.appprojet.ui.authentication.FormViewModel;
import com.example.appprojet.ui.authentication.custom_live_data.FormMutableLiveData;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Iterator;
import java.util.List;

/**
 * Request a view with the following children id :
 *  button (auth_sign_submit)
 *  textview (auth_signin_error)
 */
public abstract class FormFragment extends Fragment {

    FormViewModel viewModel;


    protected void init(View view, FormViewModel viewModel, List<TextInputLayout> textInputLayoutList, List<FormMutableLiveData> formLiveDataList) {
        this.viewModel = viewModel;
        Button submitButton = view.findViewById(R.id.auth_sign_submit);
        TextView errorTextView = view.findViewById(R.id.auth_signin_error);

        if (textInputLayoutList.size() != formLiveDataList.size())
            return;

        Iterator<TextInputLayout> textInputLayoutsIterator = textInputLayoutList.iterator();
        Iterator<FormMutableLiveData> formLiveDataIterator = formLiveDataList.iterator();
        while (textInputLayoutsIterator.hasNext() && formLiveDataIterator.hasNext()) {
            setOnFieldChanged(textInputLayoutsIterator.next(), formLiveDataIterator.next());
        }


        submitButton.setOnClickListener(v -> {
            viewModel.submitForm();
            Iterator<TextInputLayout> _textInputLayoutsIterator = textInputLayoutList.iterator();
            Iterator<FormMutableLiveData> _formLiveDataIterator = formLiveDataList.iterator();
            while (_textInputLayoutsIterator.hasNext() && _formLiveDataIterator.hasNext()) {
                setLayoutFieldError(_textInputLayoutsIterator.next(),  _formLiveDataIterator.next());
            }
        });

        viewModel.isLoadingLive.observe(this, isLoading -> {
            submitButton.setEnabled(!isLoading);
            submitButton.setText(
                    isLoading ? "Loading" : "Submit"
            );
        });


        viewModel.errorLive.observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                errorTextView.setText(error);
            } else {
                errorTextView.setVisibility(View.GONE);
            }
        });
    }


    private void setOnFieldChanged(TextInputLayout editTextLayout, FormMutableLiveData editTextLiveData) {
        EditText editText = editTextLayout.getEditText();
        editText.setText(editTextLiveData.getValue());

        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextLiveData.setValue(s.toString());
            }
        });

        editText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) setLayoutFieldError(editTextLayout, editTextLiveData);
            else editTextLayout.setError(null);
        });
    }


    private void setLayoutFieldError(TextInputLayout layout, FormMutableLiveData formMutableLiveData) {
        layout.setError(!formMutableLiveData.isValid() ? formMutableLiveData.getError() : null);
    }

}
