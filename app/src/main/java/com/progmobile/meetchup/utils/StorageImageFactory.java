package com.progmobile.meetchup.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.repositories.FirebaseStorageRepository;


public class StorageImageFactory {


    public static void fillImage(Context context, ImageView imageView, ProgressBar loadingIndicator, String docURL) {
        loadingIndicator.setVisibility(View.VISIBLE);
        FirebaseStorageRepository.getInstance().getData(docURL, new Callback<byte[]>() {
            public void onSucceed(byte[] result) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                loadingIndicator.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            }
            public void onFail(CallbackException exception) {
                loadingIndicator.setVisibility(View.GONE);
                imageView.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
                imageView.setImageDrawable(context.getDrawable(R.drawable.ic_error_image));
            }
        });
    }
}
