package com.progmobile.meetchup.ui.post_creation;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.progmobile.meetchup.R;

public class PostCreationActivity extends AppCompatActivity {

    final static int GALLERY_REQUEST_CODE = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);
        imageView = findViewById(R.id.post_image);

        final Button button = findViewById(R.id.add_document_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            // Get the URI of the file selected from the intent
            Uri selectedMedia = data.getData();
            String mimeType = computeMimeType(selectedMedia);

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
     * @return the type of the file in this format : "[type]/[extansion]". For instance,
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
