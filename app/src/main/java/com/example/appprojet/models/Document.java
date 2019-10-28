package com.example.appprojet.models;

public class Document {
    private String url;
    private MediaType media;
    public Document(String url, MediaType media) {
        this.url = url;
        this.media = media;
    }

    public String getUrl() {
        return url;
    }

    public MediaType getMedia() {
        return media;
    }

    public enum MediaType {PICTURE, VIDEO}
}
