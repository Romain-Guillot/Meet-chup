package com.example.appprojet.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarFactory {

    public static void showSnackbar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .show();
    }
}
