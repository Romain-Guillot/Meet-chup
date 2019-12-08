package com.progmobile.meetchup.repositories;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;


public class FirebaseStorageRepository implements IStorageRepository {

    public static FirebaseStorageRepository instance = null;

    private static long MAX_SIZE = 1024 * 1024;
    private FirebaseStorage storage;

    private FirebaseStorageRepository() {
        storage = FirebaseStorage.getInstance();
    }

    public static FirebaseStorageRepository getInstance() {
        synchronized (FirestoreEventsDataRepository.class) {
            if (instance == null)
                instance = new FirebaseStorageRepository();
            return instance;
        }
    }

    @Override
    public void getData(String url, Callback<byte[]> callback) {
        StorageReference imageRef;
        try {
            imageRef = storage.getReferenceFromUrl(url);
        } catch (Exception e) {
            callback.onFail(new CallbackException());
            return;
        }
        imageRef.getBytes(MAX_SIZE).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                callback.onFail(CallbackException.fromFirebaseException(task.getException()));
            } else {
                callback.onSucceed(task.getResult());
            }
        });
    }

    @Override
    public void uploadData(Uri docUri, Callback<String> callback) {
        if (docUri != null) {
            StorageReference riversRef = storage.getReference().child(docUri.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(docUri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener((Exception exception) ->
                    callback.onFail(new CallbackException(CallbackException.Type.UNKNOWN))
            ).addOnSuccessListener((UploadTask.TaskSnapshot taskSnapshot) ->
                    callback.onSucceed(riversRef.toString()));
        } else {
            callback.onSucceed(null);
        }
    }
}
