package com.example.nhdangdh.httpurlconnectionandasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText edtUrl;
    Button btnLoad;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        ivImage = (ImageView) findViewById(R.id.ivImage);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(edtUrl.getText().toString());
            }
        });
    }

    private Bitmap loadUrl(String strUrl) throws IOException {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            URL url = new URL(strUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.connect();
            in = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
        }catch (Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally {
            in.close();
        }

        return bitmap;
    }

    private class DownloadTask extends AsyncTask<String, Integer, Bitmap>{
        Bitmap bitmap = null;

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                bitmap = loadUrl(params[0]);
            }catch (Exception e){
                Log.d("Background Task", e.toString());
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivImage.setImageBitmap(bitmap);
        }
    }
}
