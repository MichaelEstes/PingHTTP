package com.michaelestes.pinghttp;

import android.support.v4.util.Pair;
import android.util.Log;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Michael.Estes on 7/19/16.
 */

public class Post extends Request {
    String body;

    public Post(String url, String endpoint, String body){
        this.url = url;
        this.endpoint = endpoint;
        this.body = body;
        method = "POST";
    }

    public Post(String url, String endpoint, String body, ArrayList<Pair<String, String>> properties){
        this(url, endpoint, body);
        this.properties = properties;
    }

    public Post(String url, String endpoint, String body, ArrayList<Pair<String, String>> params, int ignore){
        this(url, endpoint, body);
        this.params = params;
    }

    public Post(String url, String endpoint, String body, ArrayList<Pair<String, String>> properties, ArrayList<Pair<String, String>> params){
        this(url, endpoint, body);
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

                DataOutputStream dataOut = new DataOutputStream(conn.getOutputStream());
                dataOut.writeBytes(this.body);
                dataOut.flush();
                dataOut.close();

                return getConnectionResponse(conn);
            }catch (Exception e){
                Log.e(TAG, "req: couldn't connect to URL", e);
            }
        }
        return new Pair<>("Error", -1);
    }

    @Override
    public boolean isValid(){
        return validString(this.url) && validString(this.endpoint) && validString(this.body);
    }
}
