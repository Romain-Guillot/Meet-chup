 package com.progmobile.meetchup.utils;

 import android.content.Context;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.os.AsyncTask;
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

             new AsyncTask<String, Void, Bitmap> (){

            @Override
            protected Bitmap doInBackground(String... urls) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                return resize(bitmap, 720, 720);
            }

            protected void onPostExecute(Bitmap result) {
                loadingIndicator.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(result);
        }

        private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
            if (maxHeight > 0 && maxWidth > 0) {
                int width = image.getWidth();
                int height = image.getHeight();
                float ratioBitmap = (float) width / (float) height;
                float ratioMax = (float) maxWidth / (float) maxHeight;

                int finalWidth = maxWidth;
                int finalHeight = maxHeight;
                if (ratioMax > ratioBitmap) {
                    finalWidth = (int) ((float)maxHeight * ratioBitmap);
                    } else {
                        finalHeight = (int) ((float)maxWidth / ratioBitmap);
                    }
                    image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
                    return image;
                } else {
                    return image;
                }
            }
        }.execute();

         }
         public void onFail(CallbackException exception) {
         loadingIndicator.setVisibility(View.GONE);
         imageView.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
         imageView.setImageDrawable(context.getDrawable(R.drawable.ic_error_image));
         }
         });
     }
 }
