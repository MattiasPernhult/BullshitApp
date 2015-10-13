package com.example.fam.bullshitapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SpaceActivity extends FragmentActivity {

    private GoogleMap mMap;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);
        setUpMapIfNeeded();

        Intent intent = getIntent();

        controller = (Controller) intent.getSerializableExtra("controller");

        if (controller != null) {
            new MyTask().execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }
    }

    private void addMarker(LatLng latLng, String title, String desc) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(title).snippet(desc));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 8);
        mMap.animateCamera(update);
    }

    private class MyTask extends AsyncTask<LatLng, LatLng, LatLng> {

        @Override
        protected LatLng doInBackground(LatLng... latLngs) {
            return controller.getISSPosition();
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
            addMarker(latLng, "ISS", "The position of the International Space Station.");
        }
    }
}
