package com.progmobile.meetchup.utils.form_views;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.progmobile.meetchup.utils.form_data_with_validators.FormData;
import com.progmobile.meetchup.utils.form_data_with_validators.Validator;

import java.util.ArrayList;
import java.util.List;


public abstract class FormLayout<T> extends FrameLayout {

    FormData<T> formData;


    private List<OnFieldChangeListener<T>> listeners;

    public FormLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        listeners = new ArrayList<>();

    }

    @Nullable
    T getValue() {
        return this.formData == null ? null : formData.getValue();
    }

    void setValue(T value) {
        if (formData != null)
            this.formData.setValue(value);
        notifyListeners();
    }

    public void bindFormData(FormData<T> formData) {
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


    public interface OnFieldChangeListener<T> {
        void onChange(T newDate);
    }

    public abstract void setLayoutError();


    public abstract void forceUpdate();

}
