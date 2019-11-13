package com.example.appprojet.utils.form_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.example.appprojet.R;
import com.example.appprojet.utils.form_data_with_validators.BasicValidator;
import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class TextFormLayout extends FormLayout<String> {

    TextInputLayout layout;
    TextInputEditText editText;

    public TextFormLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        formData = new FormData<>(new BasicValidator());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.view_text_form_layout, null));

        this.setLayoutParams((new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)));

        layout = findViewById(R.id.text_form_text_layout);
        editText = findViewById(R.id.text_form_text_field);
        setOnTextFieldChange();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextFormLayout, 0, 0);
        try {
            String hint = a.getString(R.styleable.TextFormLayout_hint);
            layout.setHint(hint);
        } finally { a.recycle(); }
    }

    private void setOnTextFieldChange() {
        editText.setText(getValue());

        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setValue(s.toString());
            }
        });

        editText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) setLayoutFieldError(layout, formData);
            else layout.setError(null);
        });
    }

    /** Set the error indicator of the layout is the formData is not valid*/
    protected void setLayoutFieldError(TextInputLayout layout, FormData formData) {
        if (formData != null)
            layout.setError(!formData.isValid() ? formData.getError(getContext()) : null);
    }
}
