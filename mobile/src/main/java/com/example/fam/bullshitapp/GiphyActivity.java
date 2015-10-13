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
    private Bitmap gifImage;
    private byte[] imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);

        gifImageView.setOnFrameAvailable(new GifImageView.OnFrameAvailable() {
            @Override
            public Bitmap onFrameAvailable(Bitmap bitmap) {
                return bitmap;
            }
        });

        etQuestion = (EditText) findViewById(R.id.etQuestion);
        btnAskQuestion = (Button) findViewById(R.id.btnAskQuestion);
        btnAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GifDataDownloader() {
                    @Override
                    protected void onPostExecute(final byte[] bytes) {
                        gifImageView.setBytes(bytes);
                        gifImageView.startAnimation();
                        Log.d("GiphyActivity", "GIF width is " + gifImageView.getGifWidth());
                        Log.d("GiphyActivity", "GIF height is " + gifImageView.getGifHeight());
                    }
                }.execute(
                        "http://media1.giphy.com/media/QDmfwNkzcaHnO/giphy.gif");
            }
                /*
                // TODO: Skicka fråga till sentence-analyzer och kolla ifall det är en fråga, om det är det så sätt GIF:en.
                if (imageData != null) {
                    Log.d("GiphyActivity", "imageData är inte null");
                    gifImageView.setBytes(imageData);
                    gifImageView.startAnimation();
                } else {
                    Log.d("GiphyActivity", "imageData är null");
                }*/

        });

        Intent intent = getIntent();

        controller = (Controller) intent.getSerializableExtra("controller");

        if (controller != null) {
            new MyTask().execute();
        }
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

    private class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            String gifUrl = controller.getGiphy();

            Log.d("GiphyActivity", gifUrl.toString());
            URL url = null;
            try {
                url = new URL(gifUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("GiphyActivity", e.getMessage());
            }
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                int bytes = bitmap.getByteCount();

                ByteBuffer buffer = ByteBuffer.allocate(bytes);

                bitmap.copyPixelsToBuffer(buffer);

                imageData = buffer.array();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }
    }
}
