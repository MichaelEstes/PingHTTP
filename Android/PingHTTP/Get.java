package com.ticketmaster.presencelibrary.PingHTTP;

import android.support.v4.util.Pair;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.ticketmaster.presencelibrary.Utils.Utils.validString;

/**
 * Created by Michael.Estes on 7/19/16.
 */

public class Get extends Request {
    final String method = "GET";

    public Get(String url, String endpoint){
        this.url = url;
        this.endpoint = endpoint;
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
            String requestUrl = this.url;
            requestUrl += this.endpoint;
            if(this.params != null && !this.params.isEmpty()){
                requestUrl += this.formatParams();
            }

            urlTry: try{
                URL url = new URL(requestUrl);
                HttpURLConnection conn;
                if(url.getProtocol().equalsIgnoreCase("HTTPS")){
                    conn = this.createConnectionHTTPS(url);
                } else {
                    conn = this.createConnectionHTTP(url);
                }
                if(conn == null){break urlTry;}
                conn.setRequestMethod(this.method);
                conn.connect();
                return new Pair<>(IOUtils.toString(conn.getInputStream(), "UTF-8"), conn.getResponseCode());
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
