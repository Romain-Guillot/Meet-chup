package com.progmobile.meetchup.ui.event_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.ui.event_view.adapters.PhotosListViewAdapter;
import com.progmobile.meetchup.ui.post_creation.PostCreationActivity;
import com.progmobile.meetchup.ui.post_view.PostViewActivity;
import com.progmobile.meetchup.utils.views.EventMetaData;

import java.util.ArrayList;
import java.util.List;

/**
 * TBD
 */
public class AlbumFragment extends Fragment {

    private EventViewViewModel viewModel;

    private RecyclerView photosView;
    private ViewGroup emptyPhotosContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(EventViewViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_view_album, container, false);


        photosView = view.findViewById(R.id.event_album);
        photosView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.album_col)));
        emptyPhotosContainer = view.findViewById(R.id.event_empty_photo);

        FloatingActionButton addPostFAB = view.findViewById(R.id.album_add_post_fab);
        addPostFAB.setOnClickListener(v ->
                launchCreationPostActivity()
        );

        Button addPostButton = view.findViewById(R.id.event_empty_photo_add_btn);
        addPostButton.setOnClickListener(v ->
                launchCreationPostActivity()
        );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.eventPosts.observe(this, posts -> {
            posts = PreprocessListPosts(posts);
            if (posts.isEmpty()) {
                emptyPhotosContainer.setVisibility(View.VISIBLE);
                photosView.setVisibility(View.GONE);
            } else {
                emptyPhotosContainer.setVisibility(View.GONE);
                photosView.setVisibility(View.VISIBLE);
                photosView.setAdapter(new PhotosListViewAdapter(posts, post -> {
                    Intent intent = new Intent(getActivity(), PostViewActivity.class);
                    intent.putExtra(PostViewActivity.EXTRA_POST_ID, post.getId());
                    intent.putExtra(PostViewActivity.EXTRA_EVENT_ID, viewModel.eventID);
                    startActivity(intent);
                }));
            }
        });
    }

    private List<Post> PreprocessListPosts(List<Post> posts){
        List<Post> l = new ArrayList<>();
        for (Post p : posts){
            if(p.getDocUrl() != null){
                l.add(p);
            }
        }
        return l;
    }


    private void launchCreationPostActivity() {
        Intent intent = new Intent(getActivity(), PostCreationActivity.class);
        // put extras
        startActivity(intent);
    }
}
