package com.example.appprojet.models;

public class Document {
    private String id;
    private String url;

    public Document(String id, String url){
        this.id = id;
        this.url = url;
        this.media = media;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public MediaType getMedia() {
        return media;
    }

    public enum MediaType {PICTURE, VIDEO}
}
