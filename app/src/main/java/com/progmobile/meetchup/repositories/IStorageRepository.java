package com.progmobile.meetchup.repositories;

import android.net.Uri;

import com.progmobile.meetchup.utils.Callback;

public interface IStorageRepository {

    void getData(String url, Callback<byte[]> callback);

    void uploadData(Uri docUri, Callback<String> callback);
}
