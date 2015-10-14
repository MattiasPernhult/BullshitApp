package com.example.fam.bullshitapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;

public class YodaActivity extends Activity {

    private Controller controller;
    private GifImageView gifImageView;
    private TextView textView;
    private Button btnNewAdvice;
    private byte[] giphyImageOnYoda;
    private String yodaText;
    private ProgressBar progressBar;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoda);

        Intent intent = getIntent();
        controller = (Controller) intent.getSerializableExtra("controller");

        gifImageView = (GifImageView) findViewById(R.id.gifImageViewYoda);
        textView = (TextView) findViewById(R.id.tvYodaText);
        btnNewAdvice = (Button) findViewById(R.id.btnNewAdvice);
        progressBar = (ProgressBar) findViewById(R.id.progressBarYoda);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        container = (LinearLayout) findViewById(R.id.containerYoda);

        if (controller != null) {
            new MyTask().execute(Constants.GET_YODA);
        }

        setButtonListener();
    }

    private void setButtonListener() {
        btnNewAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute(Constants.GET_YODA);
            }
        });
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            gifImageView.stopAnimation();
            container.setAlpha(0.2f);
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            Thread giphy = new Thread(new Worker(Constants.GET_GIPHY));
            Thread yoda = new Thread(new Worker(Constants.GET_YODA));
            giphy.start();
            yoda.start();
            try {
                giphy.join();
                yoda.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String yodaAdvice) {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            container.setAlpha(1.0f);
            gifImageView.setBytes(giphyImageOnYoda);
            gifImageView.startAnimation();
            textView.setText(yodaText);
        }
    }

    private class Worker implements Runnable {

        private String choice;

        public Worker(String choice) {
            this.choice = choice;
        }

        private byte[] getByteArrayFromUrl(String url) throws Exception {
            if (url == null) {
                return null;
            }
            return ByteArrayHttpClient.get(url);
        }

        @Override
        public void run() {
            switch (choice) {
                case Constants.GET_GIPHY:
                    try {
                        giphyImageOnYoda = getByteArrayFromUrl(controller.getYodaGiphy());
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                case Constants.GET_YODA:
                    yodaText = controller.getYodaText();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_yoda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
