package com.progmobile.meetchup.utils;

import android.content.Context;

import com.progmobile.meetchup.R;

import java.util.Date;

public class DurationUtils {

    public static String getDurationBetweenDate(Context ctx, Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime(); // milliseconds
        int days = (int) Math.ceil(diff / (1000.*60*60*24)); // to seconds -> to minutes -> to hours -> to days
        int weeks = (int) Math.floor(days / 7.);
        int daysAfterLastWeek = days - Math.round(7 * weeks);

        String duration = "";
        if (weeks >= 1)
            duration += ctx.getResources().getQuantityString(R.plurals.week_label, weeks, weeks);
        if (daysAfterLastWeek >= 1)
            duration += ((duration.isEmpty() ? "" : ", ") +  ctx.getResources().getQuantityString(R.plurals.day_label, daysAfterLastWeek, daysAfterLastWeek));

        return duration;
    }
}
