package com.example.fam.bullshitapp;

import android.os.Build;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by mattiaspernhult on 2015-10-13.
 */
public class Controller implements Serializable {

    private String[] yesOptions = {"yes", "yeah", "hellyeah", "nodding", "nod", "yas", "hell yes", "sure", "hell to the yes"};
    private String[] noOptions = {"no", "hell no", "nope", "not happening", "oh honey no", "no bueno", "disgusted", "oh hell no",
    "hell to the no"};


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
                return null;
            }
        }
        return null;
    }

    public ArrayList<String> getISSPersons() {
        String response = HttpManager.getData(BuildUrl.getIssUrlPersons());
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                ArrayList<String> persons = new ArrayList<>();
                JSONArray jsonPersons = jsonObject.getJSONArray("people");
                for (int i = 0; i < jsonPersons.length(); i++) {
                    JSONObject person = jsonPersons.getJSONObject(i);
                    if (person.getString("craft").equals("ISS")) {
                        persons.add(person.getString("name"));
                    }
                }
                return persons;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public String getGiphy() {
        Random random = new Random();
        String yesOrNoUrl = BuildUrl.getYesOrNoUrl();
        String yesNoResult = HttpManager.getData(yesOrNoUrl);
        if (yesNoResult != null){
            String answer = null;
            try {
                answer = JsonParser.parseYesOrNo(yesNoResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (answer.equals("yes")){
                String yes = yesOptions[random.nextInt(yesOptions.length - 1)];
                String giphyUrl = BuildUrl.getGiphyUrl(yes);
                String giphyResult = HttpManager.getData(giphyUrl);
                String imageUrl = null;
                try {
                    imageUrl = JsonParser.parseGiphy(giphyResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return imageUrl;
            }
            else{
                String no = noOptions[random.nextInt(noOptions.length - 1)];
                String giphyUrl = BuildUrl.getGiphyUrl(no);
                String giphyResult = HttpManager.getData(giphyUrl);
                String imageUrl = null;
                try {
                    imageUrl = JsonParser.parseGiphy(giphyResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return imageUrl;
            }
        }
        else{
            return null;
        }

    }

}
