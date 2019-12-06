package com.progmobile.meetchup.repositories;

import com.progmobile.meetchup.utils.Callback;

public interface IStorageRepository {

    void getData(String url, Callback<byte[]> callback);

    // https://firebase.google.com/docs/storage/android/upload-files
    // void uploadData();
}
