package com.progmobile.meetchup.repositories;


import com.progmobile.meetchup.models.Event;

import java.util.Map;

public class ModelSerializer {


    /**
     * IN PROGRESS !!!
     */
    public Event dezerializeEvent(String id, Map<String, Object> data) {
        if (id == null || data == null)
            return null;
        String title;
        String invitationKey = null;
        try {
            title = (String) data.get(FirestoreEventsDataRepository.EVENT_FIELD_TITLE);
        } catch (Exception e) {
            return null;
        }
        try {
            invitationKey = (String) data.get(FirestoreEventsDataRepository.EVENT_FIELD_INVITKEY);
        } catch (Exception e) {
        }

        return new Event(id, title, null, null, null, null, null, null, invitationKey);
    }

    /**
     * IN PROGRESS !!!
     */
    public Map<String, Object> serializeEvent(Event event) {
        return null;
    }

}
