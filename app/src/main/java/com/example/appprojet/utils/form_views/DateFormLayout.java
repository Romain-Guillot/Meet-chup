package com.example.appprojet.utils.form_views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.appprojet.R;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateFormLayout extends FormLayout<Date> {

    String defaultText = "";
    MaterialButton pickerButton;
    Button removeButton;
    final DateFormat dateFormat = DateFormat.getDateInstance();



    public DateFormLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.view_date_form_layout, null));

        pickerButton = findViewById(R.id.date_form_button);
        removeButton = findViewById(R.id.date_form_delete);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DateFormLayout, 0, 0);
        try {
            defaultText = a.getString(R.styleable.DateFormLayout_text);
        } finally {
            a.recycle();
        }

        pickerButton.setText(defaultText);
        pickerButton.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        removeButton.setOnClickListener(v -> {
            setValue(null);
        });

        setValue(getValue());
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            c.set(year, monthOfYear, dayOfMonth);
            setValue(c.getTime());
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    @Override
    void setValue(Date value) {
        super.setValue(value);
        pickerButton.setText(value == null ? defaultText : dateFormat.format(value));

        pickerButton.setBackgroundTintList(getResources().getColorStateList(value == null ? R.color.disableColorLight : R.color.colorPrimaryLight));
        pickerButton.setIconTint(getResources().getColorStateList(value == null ? R.color.textColor : R.color.colorPrimary));
        pickerButton.setTextColor(getResources().getColor(value == null ? R.color.textColor : R.color.colorPrimary));
        removeButton.setVisibility(value == null ? INVISIBLE : VISIBLE);
    }
}
