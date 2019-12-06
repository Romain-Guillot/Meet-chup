package com.progmobile.meetchup.ui.post_creation;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.progmobile.meetchup.R;


public class PostCreationActivity extends AppCompatActivity {

    final static int GALLERY_REQUEST_CODE = 1;
    private ImageView imageView;
    private boolean mediaSet;
    private Button addDocumentButton;
    private FloatingActionButton sendPostButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);
        imageView = findViewById(R.id.post_image);
        imageView.setOnClickListener((View v) -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        });

        mediaSet = false;
        addDocumentButton = findViewById(R.id.add_document_button);
        addDocumentButton.setBackgroundColor(Color.rgb(0, 0, 0));
        addDocumentButton.setBackgroundResource(R.drawable.ic_add);

        addDocumentButton.setOnClickListener((View v) -> {
            if (mediaSet) {
                imageView.setImageResource(0);
                addDocumentButton.setBackgroundResource(R.drawable.ic_add);
                mediaSet = false;
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        sendPostButton = findViewById(R.id.send_post_button);
        sendPostButton.setOnClickListener((View v) -> {
            // Send post to the post view
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            // Get the URI of the file selected from the intent
            Uri selectedMedia = data.getData();
            String mimeType = computeMimeType(selectedMedia);

            mediaSet = true;
            addDocumentButton.setBackgroundResource(R.drawable.ic_delete);

            // Probably useless
            if (mimeType.startsWith("image")) {
                imageView.setImageURI(selectedMedia);
            }
        }
    }

    // Probably useless

    /**
     * Compute the mime type of a given file from its URI
     *
     * @param uri the URI of the file
     * @return the type of the file in this format : "[type]/[extension]". For instance,
     * it could return "image/png"
     */
    public String computeMimeType(Uri uri) {
        String mimeType;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getApplicationContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }

        return mimeType;
    }
}
