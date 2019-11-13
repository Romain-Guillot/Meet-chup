package com.example.appprojet.utils.form_views;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.appprojet.utils.form_data_with_validators.FormData;
import com.example.appprojet.utils.form_data_with_validators.Validator;

import java.util.ArrayList;
import java.util.List;


public abstract class FormLayout<T> extends FrameLayout {

    FormData<T> formData;


    private List<OnFieldChangeListener<T>> listeners;

    public FormLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        listeners = new ArrayList<>();

    }

    T getValue() {
        return this.formData == null ? null : formData.getValue();
    }

    void setValue(T value) {
        if (formData != null)
            this.formData.setValue(value);
        notifyListeners();
    }

    public void bindFormData(FormData formData) {
        this.formData = formData;
    }

    void setValidator(Validator<T> validator) {
        if (formData != null)
            this.formData.setValidator(validator);
    }

    private void notifyListeners() {
        for (OnFieldChangeListener<T> l : listeners) {
            l.onChange(formData.getValue());
        }
    }

    public void addOnDateChangeListener(OnFieldChangeListener listener) {
        this.listeners.add(listener);
    }


    public interface OnFieldChangeListener<T> {
        void onChange(T newDate);
    }


}
