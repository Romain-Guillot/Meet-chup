package com.example.appprojet.utils;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.appprojet.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarFactory {

    public static void showSuccessSnackbar(View view, String text) {
        showSnackbar(view, text, R.color.successColor, false);
    }

    public static void showErrorSnackbar(View view, String text) {
        showSnackbar(view, text, R.color.errorColor, false);
    }

    /** WARNING : view need to be a CoordinatorLayout !!!! */
    public static void showTopErrorSnackbar(View view, String text) {
        showSnackbar(view, text, R.color.errorColor, true);
    }

    /** WARNING : view need to be a CoordinatorLayout !!!! */
    public static void showTopSuccessSnackbar(View view, String text) {
        showSnackbar(view, text, R.color.successColor, true);
    }

    private static void showSnackbar(View view, String text, int color, boolean top) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(view.getResources().getColor(color));
        TextView snackbarActionTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        snackbarActionTextView.setTypeface(snackbarActionTextView.getTypeface(), Typeface.BOLD);
        if (top) {
            final ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
            if (params instanceof CoordinatorLayout.LayoutParams)
                ((CoordinatorLayout.LayoutParams) params).gravity = Gravity.TOP;
            else
                ((FrameLayout.LayoutParams) params).gravity = Gravity.TOP;
        }
        snackbar.show();
    }
}
