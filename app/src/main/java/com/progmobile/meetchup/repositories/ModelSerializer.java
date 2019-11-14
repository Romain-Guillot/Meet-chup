package com.progmobile.meetchup.repositories;

import com.progmobile.meetchup.models.Event;

import java.util.Date;
import java.util.Map;
import com.google.firebase.Timestamp;

public class ModelSerializer {


    /**
     * IN PROGRESS !!!
     */
    public Event dezerializeEvent(String id, Map<String, Object> data) {
        if (id == null || data == null)
            return null;
        String title;
        String invitationKey = null;
        Timestamp timestampBegin = null;
        Timestamp timestampEnd = null;
        try {
            title = (String) data.get(FirestoreEventsDataRepository.EVENT_FIELD_TITLE);
        } catch (Exception e) {
            return null;
        }

        try { invitationKey = (String) data.get(FirestoreEventsDataRepository.EVENT_FIELD_INVITKEY);}catch (Exception e){}
        try { timestampBegin = (Timestamp) data.get(FirestoreEventsDataRepository.EVENT_FIELD_DATE_BEGIN);}catch (Exception e){}
        try { timestampEnd = (Timestamp) data.get(FirestoreEventsDataRepository.EVENT_FIELD_DATE_END);}catch (Exception e){}
        Date dateBegin = timestampBegin.toDate();
        Date dateEnd = timestampEnd.toDate();
        return new Event(id, title, null, null, dateBegin, dateEnd, null, null, invitationKey);
    }

    /**
     * IN PROGRESS !!!
     */
    public Map<String, Object> serializeEvent(Event event) {
        return null;
    }

}
