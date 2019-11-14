package com.progmobile.meetchup.utils.form_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.form_data_with_validators.FormData;

import java.util.Date;

public abstract class DialogFormLayout<T> extends FormLayout<T> {

    String defaultText = "";

    MaterialButton pickerButton;
    Button removeButton;

    public DialogFormLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.view_dialog_form_layout, null));

        pickerButton = findViewById(R.id.date_form_button);
        removeButton = findViewById(R.id.date_form_delete);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DialogFormLayout, 0, 0);
        try {
            defaultText = a.getString(R.styleable.DialogFormLayout_text);
        } finally {
            a.recycle();
        }

        pickerButton.setText(defaultText);
        pickerButton.setOnClickListener(v -> {
            showDialog();
        });

        removeButton.setOnClickListener(v -> {
            setValue(null);
        });

        setValue(getValue());
    }

    @Override
    void setValue(T value) {
        super.setValue(value);
        pickerButton.setBackgroundTintList(getResources().getColorStateList(value == null ? R.color.disableColorLight : R.color.colorPrimaryLight));
        pickerButton.setIconTint(getResources().getColorStateList(value == null ? R.color.textColor : R.color.colorPrimary));
        pickerButton.setTextColor(getResources().getColor(value == null ? R.color.textColor : R.color.colorPrimary));
        removeButton.setVisibility(value == null ? INVISIBLE : VISIBLE);
    }

    @Override
    public void bindFormData(FormData<T> formData) {
        super.bindFormData(formData);
        setValue(formData.getValue());
    }

    @Override
    public void setLayoutError() {
        // nothing, no error management
    }

    abstract void showDialog();

}
