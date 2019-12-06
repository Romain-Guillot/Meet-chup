package com.progmobile.meetchup.repositories;

public interface IStorageRepository {

    byte[] getImage(String url);

    // https://firebase.google.com/docs/storage/android/upload-files
    // void uploadImage();
}
