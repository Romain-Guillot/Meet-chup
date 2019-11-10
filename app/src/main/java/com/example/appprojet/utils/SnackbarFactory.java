package com.example.appprojet.utils;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.example.appprojet.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarFactory {

    public static void showSuccessSnackbar(View view, String text) {
        showSnackbar(view, text, R.color.successColor);
    }

    public static void showErrorSnackbar(View view, String text) {
        showSnackbar(view, text, R.color.errorColor);
    }

    private static void showSnackbar(View view, String text, int color) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(view.getResources().getColor(color));
        TextView snackbarActionTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        snackbarActionTextView.setTypeface(snackbarActionTextView.getTypeface(), Typeface.BOLD);
        snackbar.show();
    }
}
