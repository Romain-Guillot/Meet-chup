package com.progmobile.meetchup.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.utils.DurationUtils;

import java.text.DateFormat;
import java.util.Date;

public class EventMetaData extends FrameLayout {

    private static DateFormat dateFormat = DateFormat.getDateInstance();

    private ImageView iconDateBegin;
    private TextView textDateBegin;
    private ImageView iconDuration;
    private TextView textDuration;
    // description
    // participants
    // location

    public EventMetaData(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.view_event_metadata, null));

        iconDateBegin = findViewById(R.id.view_event_metadata_begin_date_icon);
        textDateBegin = findViewById(R.id.view_event_metadata_begin_date);
        iconDuration = findViewById(R.id.view_event_metadata_duration_icon);
        textDuration = findViewById(R.id.view_event_metadata_duration);
    }

    public void setMetaData(Event event) {
        Date dateBegin = event.getDateBegin();
        Date dateEnd = event.getDateEnd();
        String title = event.getTitle();


        if (dateBegin != null)
            textDateBegin.setText(dateFormat.format(dateBegin));
        iconDateBegin.setVisibility(dateBegin == null ? View.GONE : View.VISIBLE);

        if (dateBegin != null && dateEnd != null)
            textDuration.setText(DurationUtils.getDurationBetweenDate(getContext(), dateBegin, dateEnd));
        iconDuration.setVisibility(dateBegin != null && dateEnd != null ? View.VISIBLE : View.GONE);
    }
}
