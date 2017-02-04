package com.michaelestes.pinghttp;

import android.os.AsyncTask;
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

    public int timeout = 10000;
    String url;
    String endpoint;
    ArrayList<Pair<String, String>> properties;
    ArrayList<Pair<String, String>> params;
    String method;

    public static final int INVALID_CONNECTION = -3, NOT_VALID = -2, EXCEPTION = -1;

    String createUrl(){
        Log.i(TAG, "Method: " + this.method + "\nURL: " + this.url + "\n Endpoint: " + this. endpoint);
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

    Response getConnectionResponse(HttpURLConnection conn){
        Response response = new Response();
        try{
            byte[] responseBody;
            boolean error = false;
            int responseCode = conn.getResponseCode();
            if(responseCode < 300){
                responseBody = IOUtils.toByteArray(conn.getInputStream());
            }else{
                responseBody = IOUtils.toByteArray(conn.getErrorStream());
                error = true;
            }
            Log.i(TAG, "Status: " + responseCode);
            response = new Response(responseCode, responseBody, error);
        }catch (Exception e){
            Log.e(TAG, "getConnectionResponse: Error getting response", e);
        } finally {
            conn.disconnect();
        }

        return response;
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
            formattedParams =  formattedParams.substring(0, formattedParams.length() - 1);
            Log.i(TAG, "Params: " + formattedParams);
            return formattedParams;
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


    class ResponseTask extends AsyncTask<Void, Void, Response> {
        RequestInterface request;
        ResponseCallback responseCallback;

        ResponseTask(RequestInterface request, ResponseCallback responseCallback){
            this.request = request;
            this.responseCallback = responseCallback;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            return request.getResponse();
        }

        @Override
        protected void onPostExecute(Response response) {
            if(response != null && !response.error){
                responseCallback.onSuccess(response);
            } else {
                responseCallback.onError(response);
            }
        }
    }
}
