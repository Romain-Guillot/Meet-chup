package com.progmobile.meetchup.ui.post_creation;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.repositories.FirebaseStorageRepository;
import com.progmobile.meetchup.repositories.FirestoreEventsDataRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.SnackbarFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class PostCreationFragment extends Fragment {

    final static int GALLERY_REQUEST_CODE = 1;
    private PostCreationViewModel viewModel;
    private EditText editText;
    private ImageView imageView;
    private boolean mediaSet;
    private MaterialButton addDocumentButton;
    private FloatingActionButton sendPostButton;

    private FirebaseStorageRepository fbStorageRepo;
    private FirestoreEventsDataRepository fsEventsDataRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null)
            throw new RuntimeException("Illegal use of PostCreationFragment");

        viewModel = ViewModelProviders.of(getActivity()).get(PostCreationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_creation, container, false);

        fbStorageRepo = FirebaseStorageRepository.getInstance();
        fsEventsDataRepo = FirestoreEventsDataRepository.getInstance();

        editText = view.findViewById(R.id.descriptionTextView);
        addDocumentButton = view.findViewById(R.id.add_document_button);
        setImageButtonSelector();
        imageView = view.findViewById(R.id.post_image);
        imageView.setOnClickListener((View v) -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        });

        mediaSet = false;

        addDocumentButton.setOnClickListener((View v) -> {
            if (mediaSet) {
                imageView.setImageResource(0);
                viewModel.setDocument(null, null);
                mediaSet = false;
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
            setImageButtonSelector();
        });

        sendPostButton = view.findViewById(R.id.send_post_button);
        sendPostButton.setOnClickListener((View v) -> {
            Post savedPost = viewModel.getPost();
            String descriptionText = editText.getText().toString();
            if (savedPost.getDocMimeType() == null && descriptionText.isEmpty())
                SnackbarFactory.showErrorSnackbar(getActivity().findViewById(android.R.id.content), "Veuilez remplir au moins un des champs.");
            else {
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), viewModel.getUri());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    byte[] data = baos.toByteArray();

                    fbStorageRepo.uploadData(viewModel.getUri(), data, new Callback<String>() {
                        public void onSucceed(String result) {
                            Date currentDate = new Date();
                            savedPost.setDescription(descriptionText);
                            Post finalPost = new Post(null, null, currentDate, descriptionText, result, savedPost.getDocMimeType());

                            fsEventsDataRepo.addPost(viewModel.event_id, finalPost, new Callback<String>() {
                                public void onSucceed(String result) {
                                    getActivity().finish();
                                }

                                public void onFail(CallbackException exception) {
                                    SnackbarFactory.showErrorSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.new_post_error));
                                }
                            });
                        }

                        public void onFail(CallbackException exception) {
                            SnackbarFactory.showErrorSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.new_post_error));
                        }
                    });
                } catch (IOException e) {
                    SnackbarFactory.showErrorSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.new_post_error));
                }
            }
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
            viewModel.setDocument(null, mimeType);
            viewModel.setUri(selectedMedia);

            mediaSet = true;
            setImageButtonSelector();

            // Probably useless
            if (mimeType.startsWith("image")) {
                imageView.setImageURI(selectedMedia);
            }
        }
    }

    public void setImageButtonSelector() {
        if (!mediaSet) {
            addDocumentButton.setText(getString(R.string.add_image));
            addDocumentButton.setIcon(getResources().getDrawable(R.drawable.ic_add));
        } else {
            addDocumentButton.setText(getString(R.string.delete_image));
            addDocumentButton.setIcon(getResources().getDrawable(R.drawable.ic_delete));
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
            imageView.setImageURI(viewModel.getUri());
        }
    }

    @Override
    public void onStop() {
        viewModel.setText(editText.getText().toString());

        super.onStop();
    }
}
