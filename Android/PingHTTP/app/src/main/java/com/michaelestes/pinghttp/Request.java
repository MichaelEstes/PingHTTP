package com.michaelestes.pinghttp;

import android.support.v4.util.Pair;
import android.util.Log;

import org.apache.commons.io.IOUtils;

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
    String method;

    String createUrl(){
        String requestUrl = this.url;
        requestUrl += this.endpoint;
        requestUrl += this.formatParams();

        return requestUrl;
    }

    HttpURLConnection createConnection(URL url){
        HttpURLConnection conn;
        if(url.getProtocol().equalsIgnoreCase("HTTPS")){
            conn = this.createConnectionHTTPS(url);
        } else {
            conn = this.createConnectionHTTP(url);
        }
        return conn;
    }

    Pair<String, Integer> getConnectionResponse(HttpURLConnection conn){
        try{
            String responseMessage = "";
            int responseCode = conn.getResponseCode();
            if(responseCode < 300){
                responseMessage = IOUtils.toString(conn.getInputStream(), "UTF-8");
            }else{
                responseMessage = IOUtils.toString(conn.getErrorStream(), "UTF-8");
            }
            return new Pair<>(responseMessage, responseCode);
        }catch (Exception e){
            Log.e(TAG, "getConnectionResponse: Error getting response", e);
        }

        return new Pair<>("Error", -1);
    }

    boolean validString(String str){
        return str != null && !str.isEmpty();
    }

    private boolean validParams(){
        return this.params != null && !this.params.isEmpty();
    }

    private String formatParams(){
        if(this.validParams()){
            String formattedParams = "?";
            for(Pair<String, String> param : this.params){
                formattedParams += param.first+"="+param.second+"&";
            }
            return formattedParams.substring(0, formattedParams.length() - 1);
        }
        return "";
    }

    private HttpsURLConnection createConnectionHTTPS(URL url){
        connTry: try{
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            if(conn == null){break connTry;}
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, null, new java.security.SecureRandom());
            conn.setSSLSocketFactory(sc.getSocketFactory());
            addProperties(conn);
            setTimeouts(conn);
            conn.setRequestMethod(this.method);
            conn.connect();
            return conn;
        } catch (Exception e){
            Log.e(TAG, "createConnection: Error creating HTTPS connection", e);
        }

        return null;
    }

    private HttpURLConnection createConnectionHTTP(URL url){
        connTry: try{
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn == null){break connTry;}
            addProperties(conn);
            setTimeouts(conn);
            conn.setRequestMethod(this.method);
            conn.connect();
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
