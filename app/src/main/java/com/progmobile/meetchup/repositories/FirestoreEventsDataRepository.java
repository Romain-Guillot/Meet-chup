package com.progmobile.meetchup.repositories;


import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.Callback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Use only method with "status : done"
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class FirestoreEventsDataRepository implements IEventsDataRepository {

    private static FirestoreEventsDataRepository instance = null;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ModelSerializer serializer;

    public final static String USERS_COL = "users";
    public final static String USERS_FIELD_EVENTS = "events";

    public final static String EVENT_COL = "events";
    public final static String EVENT_FIELD_INVITKEY = "invitKey";
    public final static String EVENT_FIELD_PARTICIPANTS = "participants";
    public final static String EVENT_FIELD_TITLE = "title";
    public final static String EVENT_FIELD_DATE_BEGIN = "dateBegin";
    public final static String EVENT_FIELD_DATE_END = "dateEnd";


    private FirestoreEventsDataRepository() {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        serializer = new ModelSerializer();
    }


    public static FirestoreEventsDataRepository getInstance() {
        synchronized (FirestoreEventsDataRepository.class) {
            if (instance == null)
                instance = new FirestoreEventsDataRepository();
            return instance;
        }
    }


    /** @inheritDoc - Status : IN PROGRESS !!!!!!
    * */
    @Override
    public void allEvents(@NonNull Activity client, @NonNull Callback<List<Event>> callback) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED)); return ;
        }
        firestore.collection(USERS_COL).document(fbUser.getUid()).addSnapshotListener(client, (documentSnapshot, e1) -> {
            if (e1 != null) { callback.onFail(CallbackException.fromFirebaseException(e1));return ; }
            try {
                List<String> eventIDs = (List<String>) documentSnapshot.getData().get(USERS_FIELD_EVENTS);
                List<Event> events = new ArrayList<>();
                if (eventIDs.isEmpty()) {
                    callback.onSucceed(events);
                    return ;
                }
                for (String id : eventIDs) {
                    firestore.collection(EVENT_COL).document(id).get().addOnCompleteListener(task -> {
                        try {
                            if (task.isSuccessful()) {
                                Event event = serializer.dezerializeEvent(task.getResult().getId(), task.getResult().getData());
                                if (event != null) {
                                    events.add(event);
                                }
                            }
                        } catch (Exception e3) {
                        } finally {callback.onSucceed(events);}
                    });
                }
            } catch (Exception e2) { callback.onSucceed(new ArrayList<>()); }
        });
    }

    /** @inheritDoc - Status : DONE
     * Load the event with the corresponding ID and if the deserialization succeeds the event is returned */
    @Override
    public void getEvent(@NonNull Activity client, @NonNull String eventID, @NonNull Callback<Event> callback) {
        firestore.collection(EVENT_COL).document(eventID).addSnapshotListener(client, ((documentSnapshot, e) -> {
            if (e != null || documentSnapshot == null && !documentSnapshot.exists()) {
                callback.onFail(CallbackException.fromFirebaseException(e)); return ;
            }
            Event event = serializer.dezerializeEvent(documentSnapshot.getId(), documentSnapshot.getData());
            if (event != null) callback.onSucceed(event);
            else callback.onFail(new CallbackException());
        }));
    }

    /** @inheritDoc - Status : IN PROGRESS
     * */
    @Override
    public void createEvent(@NonNull Event event, @NonNull Callback<String> callback) {
        firestore.collection(EVENT_COL).add(event).addOnCompleteListener(t -> {
           if (t.isSuccessful() && t.getResult() != null) {
                callback.onSucceed(t.getResult().getId());
           } else {
               callback.onFail(CallbackException.fromFirebaseException(t.getException()));
           }
        });
    }

    /** @inheritDoc - Status : DONE
     *  Look if a document with this key exists, if not the new key is set to the event */
    @Override
    public void updateEventInvitationKey(@NonNull String eventID, @NonNull String key, @NonNull Callback<String> callback) {
        firestore.collection(EVENT_COL).whereEqualTo(EVENT_FIELD_INVITKEY, key).get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful() && task1.getResult() != null && task1.getResult().getDocuments().size() >= 1 && !task1.getResult().getDocuments().get(0).getId().equals(eventID))  {
                callback.onFail(new CallbackException(CallbackException.Type.INVITATION_KEY_COLLISION));
            } else {
                firestore.collection(EVENT_COL).document(eventID)
                        .set(new HashMap<String, String>(){{put(EVENT_FIELD_INVITKEY, key);}}, SetOptions.merge())
                        .addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) callback.onSucceed(key);
                            else callback.onFail(CallbackException.fromFirebaseException(task2.getException()));
                        });
            }
        });
    }

    /** @inheritDoc - Status : DONE
     * Just delete the invitation field of the event */
    @Override
    public void deleteEventInvitationKey(@NonNull String eventID, @NonNull Callback<Void> callback) {
        firestore.collection(EVENT_COL).document(eventID)
                .set(new HashMap<String, Object>(){{put(EVENT_FIELD_INVITKEY, FieldValue.delete());}}, SetOptions.merge())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) callback.onSucceed(null);
                    else callback.onFail(CallbackException.fromFirebaseException(task.getException()));
                });
    }

    /** @inheritDoc - Status : DONE
     * Get the correct event doc, add the user to it, add the event ID in the user event list */
    @Override
    public void joinEvent(@NonNull String invitationKey, @NonNull Callback<String> callback) {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED));return ;
        }
        firestore.collection(EVENT_COL).whereEqualTo(EVENT_FIELD_INVITKEY, invitationKey).get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful() && task1.getResult() != null && task1.getResult().getDocuments().size() == 1) {
                DocumentSnapshot docEventSnap = task1.getResult().getDocuments().get(0);
                String eventID = docEventSnap.getId();
                String userID = fbUser.getUid();
                WriteBatch writeBatch = firestore.batch();
                DocumentReference eventDocRef = docEventSnap.getReference();
                DocumentReference userDocRef = firestore.collection(USERS_COL).document(userID);
                writeBatch.set(userDocRef, new HashMap<String, Object>(){{put(USERS_FIELD_EVENTS, FieldValue.arrayUnion(eventID));}}, SetOptions.merge());
                writeBatch.set(eventDocRef, new HashMap<String, Object>(){{put(EVENT_FIELD_PARTICIPANTS, FieldValue.arrayUnion(userID));}}, SetOptions.merge());
                writeBatch.commit().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful())  callback.onSucceed(eventID);
                    else callback.onFail(CallbackException.fromFirebaseException(task2.getException()));
                });
            } else {
                callback.onFail(CallbackException.fromFirebaseException(task1.getException()));
            }
        });
    }

    /** @inheritDoc - Status : WIP
     *  Update the user event list and the event user list */
    @Override
    public void quitEvent(@NonNull String eventID, @NonNull Callback<Void> callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            callback.onFail(new CallbackException(CallbackException.Type.NO_LOGGED));return ;
        }
        WriteBatch writeBatch = firestore.batch();
        DocumentReference userDoc = firestore.collection(USERS_COL).document(user.getUid());
        DocumentReference eventDoc = firestore.collection(EVENT_COL).document(eventID);
        writeBatch.set(userDoc, new HashMap<String, Object>(){{put(USERS_FIELD_EVENTS, FieldValue.arrayRemove(eventID));}}, SetOptions.merge());
        writeBatch.set(eventDoc, new HashMap<String, Object>(){{put(EVENT_FIELD_PARTICIPANTS, FieldValue.arrayRemove(user.getUid()));}}, SetOptions.merge());
        writeBatch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) callback.onSucceed(null);
            else callback.onFail(CallbackException.fromFirebaseException(task.getException()));
        });
    }
}
