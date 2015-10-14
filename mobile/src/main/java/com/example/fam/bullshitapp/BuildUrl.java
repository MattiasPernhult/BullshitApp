package com.example.fam.bullshitapp;

import android.util.Log;

import com.example.fam.bullshitapp.credentials.ApiKeys;

import org.apache.http.client.utils.URIUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

/**
 * Created by mattiaspernhult on 2015-10-13.
 */
public class BuildUrl {

    public static String getYesOrNoUrl(){
        return "http://www.yesno.wtf/api";
    }

    public static String getGiphyUrl(String query){
        String url = "http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=" + ApiKeys.giphyKey + "&limit=100";
        return url;
    }

    public static String getIssUrlPosition() {
        return "http://api.open-notify.org/iss-now.json";
    }

    public static String getIssUrlPersons() {
        return "http://api.open-notify.org/astros.json";
    }

    public static String getAdviceUrl() {
        return "http://api.adviceslip.com/advice";
    }

    public static String getYodaUrl(String advice) {
        String validURLString = "https://yoda.p.mashape.com/yoda?sentence=" + advice;
        validURLString = validURLString.replace(' ', '+');
        validURLString = validURLString.replace('"', '\'');
        Log.d("YodaActivity", validURLString);
        return validURLString;
    }
}
