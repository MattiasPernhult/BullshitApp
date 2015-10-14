package com.example.fam.bullshitapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.felipecsl.gifimageview.library.GifImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

public class GiphyActivity extends Activity {

    private Controller controller;
    private GifImageView gifImageView;
    private EditText etQuestion;
    private Button btnAskQuestion;
    private byte[] gifImageInBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);

        /*
        gifImageView.setOnFrameAvailable(new GifImageView.OnFrameAvailable() {
            @Override
            public Bitmap onFrameAvailable(Bitmap bitmap) {
                return bitmap;
            }
        });
        */

        etQuestion = (EditText) findViewById(R.id.etQuestion);
        btnAskQuestion = (Button) findViewById(R.id.btnAskQuestion);

        setButtonListener();

        Intent intent = getIntent();

        controller = (Controller) intent.getSerializableExtra("controller");

        if (controller != null) {
            new MyTask().execute();
        }
    }

    private void setButtonListener() {
        btnAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Skicka fråga till sentence-analyzer och kolla ifall det är en fråga, om det är det så sätt GIF:en.
                if (gifImageInBytes != null) {
                    Log.d("GiphyActivity", "imageData är inte null");
                    gifImageView.setBytes(gifImageInBytes);
                    gifImageView.startAnimation();
                } else {
                    Log.d("GiphyActivity", "imageData är null");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_giphy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyTask extends AsyncTask<String, String, Void>{

        private byte[] getByteArrayFromUrl(String url) throws Exception {
            if (url == null) {
                return null;
            }
            return ByteArrayHttpClient.get(url);
        }

        @Override
        protected Void doInBackground(String... params) {
            String gifUrl = controller.getGiphy();
            Log.d("GiphyActivity", gifUrl.toString());
            try {
                byte[] byteImage = getByteArrayFromUrl(gifUrl);
                gifImageInBytes = byteImage;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("GiphyActivity", e.getMessage());
            }
            return null;
        }
    }
}
