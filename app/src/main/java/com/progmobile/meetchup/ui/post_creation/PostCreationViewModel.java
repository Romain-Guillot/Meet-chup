package com.progmobile.meetchup.ui.post_creation;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.progmobile.meetchup.models.Post;

public class PostCreationViewModel extends ViewModel {
    private Post post;
    private Uri uri;
    public String event_id = null;

    public PostCreationViewModel() {
        this.post = new Post();
    }

    void setText(String description) {
        this.post.setDescription(description);
    }

    void setDocument(String docURL, String docMimeType) {
        this.post.setDocURL(docURL);
        this.post.setDocMimeType(docMimeType);
    }

    public Post getPost() {
        return post;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }
}
