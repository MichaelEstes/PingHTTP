package com.michaelestes.pinghttp;

import android.support.v4.util.Pair;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Michael.Estes on 7/19/16.
 */

public class Get extends Request {
    public Get(String url, String endpoint){
        this.url = url;
        this.endpoint = endpoint;
        this.method = "GET";
    }

    public Get(String url, String endpoint, ArrayList<Pair<String, String>> properties){
        this(url, endpoint);
        this.properties = properties;
    }

    public Get(String url, String endpoint, ArrayList<Pair<String, String>> params, int ignore){
        this(url, endpoint);
        this.params = params;
    }

    public Get(String url, String endpoint, ArrayList<Pair<String, String>> properties, ArrayList<Pair<String, String>> params){
        this(url, endpoint);
        this.properties = properties;
        this.params = params;
    }

    @Override
    public Pair<String, Integer> req() {
        if(this.isValid()){
            String requestUrl = createUrl();

            urlTry: try{
                URL url = new URL(requestUrl);
                HttpURLConnection conn;
                conn = createConnection(url);
                if(conn == null){break urlTry;}
                return getConnectionResponse(conn);
            }catch (Exception e){
                Log.e(TAG, "req: couldn't connect to URL", e);
            }
        }
        return new Pair<>("Error", -1);
    }

    @Override
    public boolean isValid(){
        return validString(this.url) && validString(this.endpoint);
    }
}
