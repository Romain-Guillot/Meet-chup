package com.progmobile.meetchup.utils.form_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.utils.Location;
import com.progmobile.meetchup.utils.form_data_with_validators.BasicValidator;
import com.progmobile.meetchup.utils.form_data_with_validators.FormData;


public class LocationFormLayout extends DialogFormLayout<Location> {

    MaterialAlertDialogBuilder builder;
    AlertDialog dialog;

    public LocationFormLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pickerButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_location));

        builder = new MaterialAlertDialogBuilder(getContext());
    }

    @Override
    void showDialog() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.view_choose_location, null);
        TextFormLayout layout = dialogLayout.findViewById(R.id.location_picker_input);

        if (getValue() != null)
            layout.setText(getValue().getLocation());

        builder.setView(dialogLayout)
                // Add action buttons
                .setTitle("Choose location")
                .setPositiveButton("Ok", (dialog, id) -> {
                    String location = layout.getValue();
                    if (location != null && !location.isEmpty())
                        setValue(new Location(layout.getValue()));
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                        dialog.cancel();
                });
        dialog  = builder.create();
        dialog.show();
    }

    @Override
    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }


    @Override
    void setValue(Location value) {
        super.setValue(value);
        pickerButton.setText(value == null ? defaultText : value.getLocation());
    }

    @Override
    public void forceUpdate() {
        setLocation(getValue());
    }

    public void setLocation(Location location) {
        setValue(location);
    }
}
