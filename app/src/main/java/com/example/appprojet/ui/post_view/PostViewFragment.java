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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojet.R;
import com.example.appprojet.models.Document;
import com.example.appprojet.models.Post;
import com.example.appprojet.utils.DownloadImage;


public class PostViewFragment extends Fragment {

    private PostViewViewModel viewModel;

    private TextView descriptionView;
    private ImageView imageView;
    private RecyclerView commentsView;
    private ViewGroup emptyCommentsContainer;

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
        this.commentsView = view.findViewById(R.id.post_comments);
        commentsView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        emptyCommentsContainer = view.findViewById(R.id.post_empty_comments);


        viewModel.postDescriptionLive.observe(this, description -> {
            descriptionView.setText(description);
        });

        viewModel.postImageLive.observe(this, url -> {
            new DownloadImage(imageView).execute(url);
        });

        viewModel.postCommentsLive.observe(this, comments -> {
            if(comments.isEmpty()){
                emptyCommentsContainer.setVisibility(View.VISIBLE);
                commentsView.setVisibility(View.GONE);
            } else {
                emptyCommentsContainer.setVisibility(View.GONE);
                commentsView.setVisibility(View.VISIBLE);
                commentsView.setAdapter(new CommentsListViewAdapter(comments));
            }
        });

        return view;
    }
}
