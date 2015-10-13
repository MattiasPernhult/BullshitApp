package com.example.fam.bullshitapp;

import com.example.fam.bullshitapp.credentials.ApiKeys;

/**
 * Created by mattiaspernhult on 2015-10-13.
 */
public class BuildUrl {

    public static String getYesOrNoUrl(){
        return "www.yesno.wtf/api";
    }

    public static String getGiphyUrl(String query){
        String url = "http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=" + ApiKeys.giphyKey;
        return url;
    }

    public static String getIssUrlPosition() {
        return "http://api.open-notify.org/iss-now.json";
    }

    public static String getIssUrlPersons() {
        return "http://api.open-notify.org/astros.json";
    }
}
