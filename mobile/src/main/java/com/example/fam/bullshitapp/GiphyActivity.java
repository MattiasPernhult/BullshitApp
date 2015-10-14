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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

public class GiphyActivity extends Activity {

    // TODO: Add Toast message with some funny message dependin of the answer (either yes or no)

    private Controller controller;
    private GifImageView gifImageView;
    private EditText etQuestion;
    private Button btnAskQuestion;
    private byte[] gifImageInBytes;
    private ProgressBar progressBar;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        container = (LinearLayout) findViewById(R.id.container);
        container.setVisibility(View.VISIBLE);

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
    }

    private void setButtonListener() {
        btnAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etQuestion.getText().length() <= 0){
                    Toast.makeText(GiphyActivity.this, "You must ask a question!", Toast.LENGTH_SHORT).show();
                }else{
                    new MyTask().execute();
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

    private class MyTask extends AsyncTask<String, String, byte[]>{

        @Override
        protected void onPreExecute() {
            btnAskQuestion.setEnabled(false);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            Log.d("GiphyActivity", "background: " + container.getAlpha());
            container.setAlpha(0.2f);
            gifImageView.stopAnimation();
        }

        private byte[] getByteArrayFromUrl(String url) throws Exception {
            if (url == null) {
                return null;
            }
            return ByteArrayHttpClient.get(url);
        }

        @Override
        protected byte[] doInBackground(String... params) {
            String gifUrl = controller.getGiphy();
            Log.d("GiphyActivity", gifUrl.toString());
            try {
                return getByteArrayFromUrl(gifUrl);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("GiphyActivity", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(byte[] imageByte) {
            btnAskQuestion.setEnabled(true);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            container.setAlpha(1.0f);
            if (imageByte == null){
                Toast.makeText(GiphyActivity.this, "Oops, your mother blocked this content, try again!", Toast.LENGTH_LONG);
                gifImageView.startAnimation();
                return;
            }
            gifImageView.setBytes(imageByte);
            gifImageView.startAnimation();
        }
    }
}
