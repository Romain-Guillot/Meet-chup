package com.progmobile.meetchup.repositories;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.progmobile.meetchup.models.Event;
import com.progmobile.meetchup.utils.Callback;

import java.util.List;

/**
 * As operations are asynchronous with the database, the callback system is used to notify client
 * when the operation succeeds or fails : {@link Callback}
 * <p>
 * IMPORTANT : Sometimes the functions require an activity as a parameter.That's because some
 * function adds listeners on objects, so we have to delete these listeners when the client activity
 * is no longer active and so and therefore no longer needs to listen these objects.
 * These functions are generally READ operations (get..., all... in opposition to other types of
 * operations : update..., delete..., create...)
 */
public interface IEventsDataRepository {

    int INVITATION_KEY_MIN_LENGTH = 8;
    int INVITATION_KEY_MAX_LENGTH = 20;


    /**
     * Get all user events
     * If succeeds list of all events are returned (can be empty)
     * -> Every user event update will be notified through the callback
     */
    void allEvents(@NonNull Activity client, @NonNull Callback<List<Event>> callback);

    /**
     * Get the event corresponding to the ID
     * The event is returned through the callback is it exists, else an exception is returned
     * -> Every event update will be notified through the callback
     */
    void getEvent(@NonNull Activity client, @NonNull String eventID, @NonNull Callback<Event> callback);

    /**
     * An event is created in the database
     * The created event ID is returned through the callback
     */
    void createEvent(@NonNull Event event, @NonNull Callback<String> callback);

    /**
     * Update the invitation key of the event
     * The callback returns the new key if success, an exception else
     */
    void updateEventInvitationKey(@NonNull String eventID, @NonNull String key, @NonNull Callback<String> callback);

    /**
     * Remove the current invitation key of the event
     * Nothing returned if success, else an exception is returned
     */
    void deleteEventInvitationKey(@NonNull String eventID, @NonNull Callback<Void> callback);

    /**
     * Add current user as participant of the event
     * Event ID returned if success, else an exception is returned
     */
    void joinEvent(@NonNull String invitationKey, @NonNull Callback<String> callback);

    /**
     * Remove the user from the event
     * Nothing returned if success, else an exception is returned
     */
    void quitEvent(@NonNull String eventID, @NonNull Callback<Void> callback);


//    public void loadEventPosts(String eventID, Callback<Event> callback);
//
//    public void loadEventToDoList(String eventID, Callback<Event> callback);
//
//
//    public void getPost(String post_id, Callback<Post> callback);
//
//    public void addPost(String eventID, Post post, Callback<Post> callback);
//
//    public void deletePost(String eventID,Post post, Callback<Boolean> callback);
//
//    public void loadPostComments(Post post, Callback<Post> callback);

}
