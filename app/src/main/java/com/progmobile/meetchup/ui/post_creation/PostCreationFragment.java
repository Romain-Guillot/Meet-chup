package com.progmobile.meetchup.ui.post_creation;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Post;

public class PostCreationFragment extends Fragment {

    final static int GALLERY_REQUEST_CODE = 1;
    private PostCreationViewModel viewModel;
    private EditText editText;
    private ImageView imageView;
    private boolean mediaSet;
    private Button addDocumentButton;
    private FloatingActionButton sendPostButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null)
            throw new RuntimeException("Illegal use of ProfileEditFragment");

        viewModel = ViewModelProviders.of(getActivity()).get(PostCreationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_creation, container, false);

        editText = view.findViewById(R.id.descriptionTextView);
        imageView = view.findViewById(R.id.post_image);
        imageView.setOnClickListener((View v) -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        });

        mediaSet = false;
        addDocumentButton = view.findViewById(R.id.add_document_button);
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

        sendPostButton = view.findViewById(R.id.send_post_button);
        sendPostButton.setOnClickListener((View v) -> {
            // Send post to the post view
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            // Get the URI of the file selected from the intent
            Uri selectedMedia = data.getData();
            String mimeType = computeMimeType(selectedMedia);
            viewModel.setDocument(selectedMedia.toString(), mimeType);

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
    private String computeMimeType(Uri uri) {
        String mimeType;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }

        return mimeType;
    }

    @Override
    public void onStart() {
        super.onStart();

        Post post = viewModel.getPost();
        if (post.getDescription() != null)
            editText.setText(post.getDescription());

        String mimeType = post.getDocMimeType();
        if (mimeType != null && mimeType.startsWith("image")) {
            imageView.setImageURI(Uri.parse(post.getDocURL()));
        }
    }

    @Override
    public void onStop() {
        viewModel.setText(editText.getText().toString());

        super.onStop();
    }
}
