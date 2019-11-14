package com.progmobile.meetchup.models;

public class Document {
    private String mimeType;
    private String url;

    public Document(String id, String mimeType) {
        this.mimeType = mimeType;
        this.url = url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getUrl() {
        return url;
    }
}
