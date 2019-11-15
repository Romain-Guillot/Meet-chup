package com.progmobile.meetchup.utils.form_views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.form_data_with_validators.BasicValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.FormData;


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

            int inputType = a.getInteger(R.styleable.TextFormLayout_inputType, 0);
            switch (inputType) {
                case 0 : // text
                    layout.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    break;
                case 1: // password
                    ColorStateList colorTint = a.getColorStateList(R.styleable.TextFormLayout_colorTint);
                    if (colorTint != null)
                        layout.setEndIconTintList(colorTint);
                    break;
                case 2: // email
                    layout.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;
                case 3: // singleLine
                    layout.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    editText.setSingleLine(true);
                    break;
            }
        } finally { a.recycle(); }
    }

    @Override
    void setValue(String value) {
        super.setValue(value);
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
            if (!hasFocus) setLayoutError();
            else layout.setError(null);
        });
    }

    @Override
    public void bindFormData(FormData<String> formData) {
        super.bindFormData(formData);
        editText.setText(formData.getValue());
    }

    /** Set the error indicator of the layout is the formData is not valid*/
    @Override
    public void setLayoutError() {
        if (formData != null)
            layout.setError(!formData.isValid() ? formData.getError(getContext()) : null);
    }

    public void setText(String text) {
        editText.setText(text);
    }
}
