package com.example.fam.bullshitapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Felix on 2015-10-13.
 */
public class JsonParser {

    public static String parseYesOrNo(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String answer = jsonObject.getString("answer");
        return answer;
    }

    public static String parseGiphy(String json) throws JSONException {
        Random random = new Random();
        JSONObject jsonRootObject = new JSONObject(json);
        JSONArray array = jsonRootObject.getJSONArray("data");
        JSONObject jsonObject = array.getJSONObject(random.nextInt(array.length() - 1));
        JSONObject images = jsonObject.getJSONObject("images");
        JSONObject specificImage = images.getJSONObject("original");
        String imageUrl = specificImage.getString("url");
        return imageUrl;
    }
}
