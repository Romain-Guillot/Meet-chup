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
        Date dateBegin = null;
        Date dateEnd = null;
        String invitationKey = null;

        try {
            title = (String) data.get(FirestoreEventsDataRepository.EVENT_FIELD_TITLE);
        } catch (Exception e) {
            return null;
        }

        try {
            invitationKey = (String) data.get(FirestoreEventsDataRepository.EVENT_FIELD_INVITKEY);
        }catch (Exception e){}
        try {
            Timestamp timestampBegin = (Timestamp) data.get(FirestoreEventsDataRepository.EVENT_FIELD_DATE_BEGIN);
            dateBegin = timestampBegin.toDate();
        }catch (Exception e){}
        try {
            Timestamp timestampEnd = (Timestamp) data.get(FirestoreEventsDataRepository.EVENT_FIELD_DATE_END);
            dateEnd = timestampEnd.toDate();
        }catch (Exception e){}

        return new Event(id, title, null, null, dateBegin, dateEnd, null, null, invitationKey);
    }

    /**
     * IN PROGRESS !!!
     */
    public Map<String, Object> serializeEvent(Event event) {
        return null;
    }

}
