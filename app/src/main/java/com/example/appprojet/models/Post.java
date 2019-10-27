package com.example.appprojet.models;

import java.util.List;

public class Post {
    private String description;
    private List<Document> documentsList;

    public Post(String description, List<Document> documentsList){
        this.description = description;
        this.documentsList = documentsList;
    }

    public List<Document> getDocumentsList() {
        return documentsList;
    }

    public String getDescription() {
        return description;
    }
}
