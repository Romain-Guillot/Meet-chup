package com.progmobile.meetchup.repositories;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
        StorageReference imageRef = storage.getReferenceFromUrl(url);
        imageRef.getBytes(MAX_SIZE).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                System.err.println("OK1");
                callback.onFail(CallbackException.fromFirebaseException(task.getException()));
            } else {
                System.err.println("OK2");
                callback.onSucceed(task.getResult());
            }
        });
    }

}
