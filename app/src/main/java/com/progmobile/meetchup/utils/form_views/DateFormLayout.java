package com.progmobile.meetchup.utils.form_views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.progmobile.meetchup.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateFormLayout extends DialogFormLayout<Date> {

    final DateFormat dateFormat = DateFormat.getDateInstance();

    public DateFormLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pickerButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_calendar));

    }

    @Override
    void showDialog() {
        final Calendar c = Calendar.getInstance();
        if (getValue() != null)
            c.setTime(getValue());
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
    }



    public void setDate(Date date) {
        setValue(date);
    }
}
