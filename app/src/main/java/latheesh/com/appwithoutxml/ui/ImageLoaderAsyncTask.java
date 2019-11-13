package latheesh.com.appwithoutxml.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import latheesh.com.appwithoutxml.R;

public class ImageLoaderAsyncTask extends AsyncTask<Void,Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private ImageView imageView;
    private String imageUrl;

    @SuppressLint("StaticFieldLeak")
    private Context context;
    public ImageLoaderAsyncTask(Context context,ImageView imageView, String imageUrl)
    {
        this.imageView=imageView;
        this.context=context;
        this.imageUrl=imageUrl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_background,context.getTheme()));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap!=null)
        {
            imageView.setImageBitmap(bitmap);

        }
        else
        {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_background,context.getTheme()));
        }

    }

    @Override
    protected Bitmap doInBackground(Void... voids) {

        return getImageBitmap(imageUrl);
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL imageurl = new URL(url);
            URLConnection conn = imageurl.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Image", "Failed to load the image from the url: " + url, e);
        }
        return bm;
    }

}
