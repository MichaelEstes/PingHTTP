package com.ticketmaster.presencelibrary.PingHTTP;

import android.support.v4.util.Pair;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 * Created by Michael.Estes on 7/19/16.
 */

public abstract class Request {
    final String TAG = "PingHTTP";

    int timeout = 10000;
    String url;
    String endpoint;
    ArrayList<Pair<String, String>> properties;
    ArrayList<Pair<String, String>> params;

    public String formatParams(){
        if(this.params != null && !this.params.isEmpty()){
            String formattedParams = "?";
            for(Pair<String, String> param : this.params){
                formattedParams += param.first+"="+param.second+"&";
            }
            return formattedParams.substring(0, formattedParams.length() - 1);
        }
        return "";
    }

    HttpsURLConnection createConnectionHTTPS(URL url){
        connTry: try{
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            if(conn == null){break connTry;}
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, null, new java.security.SecureRandom());
            conn.setSSLSocketFactory(sc.getSocketFactory());
            addProperties(conn);
            setTimeouts(conn);
            return conn;
        } catch (Exception e){
            Log.e(TAG, "createConnection: Error creating HTTPS connection", e);
        }

        return null;
    }

    HttpURLConnection createConnectionHTTP(URL url){
        connTry: try{
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn == null){break connTry;}
            addProperties(conn);
            setTimeouts(conn);
            return conn;
        } catch (Exception e){
            Log.e(TAG, "createConnection: Error creating HTTP connection", e);
        }

        return null;
    }

    private void addProperties(URLConnection conn){
        if(properties != null && !properties.isEmpty()){
            for(Pair<String, String> property : properties){
                conn.addRequestProperty(property.first, property.second);
            }
        }
    }

    private void setTimeouts(URLConnection conn){
        conn.setReadTimeout(timeout);
        conn.setConnectTimeout(timeout);
    }

    public abstract Pair<String, Integer> req();
    public abstract boolean isValid();
}
