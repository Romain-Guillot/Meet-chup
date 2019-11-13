package com.progmobile.meetchup.models;

public class Document {
    private String id;
    private String url;

    public Document(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
