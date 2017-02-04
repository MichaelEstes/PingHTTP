package com.michaelestes.pinghttp;

import android.support.v4.util.Pair;

import java.util.ArrayList;

/**
 * Created by michaelestes on 12/20/16.
 */

public class Put extends Post {

    public Put(String url, String endpoint, String body){
        super(url, endpoint, body);
        method = "PUT";
    }

    public Put(String url, String endpoint, String body, ArrayList<Pair<String, String>> properties){
        super(url, endpoint, body, properties);
        method = "PUT";
    }

    public Put(String url, String endpoint, String body, ArrayList<Pair<String, String>> params, int ignore){
        super(url, endpoint, body, params, ignore);
        method = "PUT";
    }

    public Put(String url, String endpoint, String body, ArrayList<Pair<String, String>> properties, ArrayList<Pair<String, String>> params){
        super(url, endpoint, body, properties, params);
        method = "PUT";
    }
}
