package com.example.fam.bullshitapp;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mattiaspernhult on 2015-10-13.
 */
public class Controller implements Serializable {

    public Controller() {

    }

    public LatLng getISSPosition() {
        String response = HttpManager.getData(BuildUrl.getIssUrlPosition());
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject position = jsonObject.getJSONObject("iss_position");
                LatLng latLng = new LatLng(position.getDouble("latitude"), position.getDouble("longitude"));
                return latLng;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}