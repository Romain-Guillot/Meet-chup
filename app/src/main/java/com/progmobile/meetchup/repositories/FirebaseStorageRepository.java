package com.progmobile.meetchup.repositories;

import com.google.firebase.storage.FirebaseStorage;


public class FirebaseStorageRepository implements IStorageRepository {

    public static FirebaseStorageRepository instance = null;

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
    public byte[] getImage(String url) {

        return new byte[0];
    }

}
