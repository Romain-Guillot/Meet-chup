package com.progmobile.meetchup.ui.event_view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.repositories.FirebaseStorageRepository;
import com.progmobile.meetchup.repositories.IStorageRepository;
import com.progmobile.meetchup.utils.Callback;
import com.progmobile.meetchup.utils.CallbackException;
import com.progmobile.meetchup.utils.Icon;

import java.util.List;

public class PhotosListViewAdapter extends RecyclerView.Adapter<PhotosListViewAdapter.PhotoViewHolder> {

    private static IStorageRepository storageRepository;
    private List<Post> posts;
    private PhotosListViewAdapter.OnItemClickListener listener;

    public PhotosListViewAdapter(List<Post> posts, OnItemClickListener clickListener) {
        this.posts = posts;
        this.listener = clickListener;
        storageRepository = FirebaseStorageRepository.getInstance();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        View view;
        Icon imageView;
        ProgressBar loadingView;
        Context context;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.imageView = itemView.findViewById(R.id.item_photo_image);
            this.loadingView = itemView.findViewById(R.id.item_photo_loading);
            context = itemView.getContext();
        }

        public void bind(Post post, final PhotosListViewAdapter.OnItemClickListener listener) {
            if (listener != null)
                view.setOnClickListener(v -> listener.onItemClick(post));

            if (post == null) {
                loadingView.setVisibility(View.GONE);
                imageView.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
                imageView.setImageDrawable(context.getDrawable(R.drawable.ic_error_image));
                return ;
            }

            String docURL = post.getDocUrl();
            if (docURL != null) {
                loadingView.setVisibility(View.VISIBLE);
                PhotosListViewAdapter.storageRepository.getData(docURL, new Callback<byte[]>() {
                    public void onSucceed(byte[] result) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                        imageView.setImageBitmap(bitmap);
                        loadingView.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                    public void onFail(CallbackException exception) {
                        loadingView.setVisibility(View.GONE);
                        imageView.setImageDrawable(context.getDrawable(R.drawable.ic_error_image));
                    }
                });
            } else {
                imageView.setVisibility(View.GONE);
                loadingView.setVisibility(View.GONE);
            }
        }
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_photo, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(postView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post, listener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public interface OnItemClickListener{
        void onItemClick(Post post);
    }
}
