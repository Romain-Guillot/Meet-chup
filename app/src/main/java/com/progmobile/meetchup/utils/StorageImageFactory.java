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

 import java.io.InputStream;


 public class StorageImageFactory {

     private static class Task extends AsyncTask<String, Void, Bitmap> {

         byte[] result;
         View loadingIndicator;
         ImageView imageView;

         Task(byte[] result, View loadingIndicator, ImageView imageView) {
             this.result = result;
             this.loadingIndicator = loadingIndicator;
             this.imageView = imageView;
         }

         @Override
         protected Bitmap doInBackground(String... urls) {
             BitmapFactory.Options options = new BitmapFactory.Options();
             options.inJustDecodeBounds = true;
             BitmapFactory.decodeByteArray(result, 0, result.length, options);
             float aspectRatio = options.outWidth / (float) options.outHeight;
             options.inJustDecodeBounds = false;
             options.inSampleSize = calculateInSampleSize(options, 720, (int) (720 / aspectRatio));
             Bitmap tmpBitmap = BitmapFactory.decodeByteArray(result, 0, result.length, options);
             Bitmap bmp = Bitmap.createScaledBitmap(tmpBitmap, 720, (int) (720 / aspectRatio), true);

             return bmp;
         }

         protected void onPostExecute(Bitmap result) {
             loadingIndicator.setVisibility(View.GONE);
             imageView.setVisibility(View.VISIBLE);
             imageView.setImageBitmap(result);
         }
     }

     public static void fillImage(Context context, ImageView imageView, ProgressBar loadingIndicator, String docURL) {
         loadingIndicator.setVisibility(View.VISIBLE);
         FirebaseStorageRepository.getInstance().getData(docURL, new Callback<byte[]>() {
         public void onSucceed(byte[] result) {

             new Task(result, loadingIndicator, imageView).execute();

         }
         public void onFail(CallbackException exception) {
         loadingIndicator.setVisibility(View.GONE);
         imageView.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
         imageView.setImageDrawable(context.getDrawable(R.drawable.ic_error_image));
         }
         });
     }

     public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
         final int height = options.outHeight;
         final int width = options.outWidth;
         int inSampleSize = 1;
         if (height > reqHeight || width > reqWidth) {
             final int halfHeight = height / 2;
             final int halfWidth = width / 2;
             while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth)
                 inSampleSize *= 2;
         }
         return inSampleSize;
     }
 }
