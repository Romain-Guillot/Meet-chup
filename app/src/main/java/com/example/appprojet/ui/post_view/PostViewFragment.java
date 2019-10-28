package com.example.appprojet.ui.post_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.appprojet.R;
import com.example.appprojet.models.Document;
import com.example.appprojet.models.Post;
import com.example.appprojet.utils.DownloadImage;


public class PostViewFragment extends Fragment {

    private PostViewViewModel viewModel;

    private TextView descriptionView;
    private ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(PostViewViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_view, container, false);

        descriptionView = view.findViewById(R.id.post_view_description);
        imageView = view.findViewById(R.id.post_view_image);


        viewModel.postLive.observe(this, post -> {
            String description = post.getDescription();
            if (description != null)
                descriptionView.setText(description);

            Document doc = post.getDocument();
            String url = doc.getUrl();
            if (url != null)
                new DownloadImage(imageView).execute(post.getDocument().getUrl());
        });



        return view;
    }
}
