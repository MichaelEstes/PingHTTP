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

public class Post extends Request implements RequestInterface {
    String body;

    Post(){}

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
    public void req(ResponseCallback responseCallback) {
        ResponseTask responseTask = new ResponseTask(this, responseCallback);
        responseTask.execute();
    }

    @Override
    public Response getResponse() {
        if(this.isValid()){
            String requestUrl = createUrl();

            urlTry: try{
                URL url = new URL(requestUrl);
                HttpURLConnection conn;
                long startTime = System.currentTimeMillis();
                conn = createConnection(url);
                if(conn == null){break urlTry;}
                Log.i(TAG, "Body :" + this.body);
                DataOutputStream dataOut = new DataOutputStream(conn.getOutputStream());
                dataOut.writeBytes(this.body);
                dataOut.flush();
                dataOut.close();
                Response response = getConnectionResponse(conn);
                long responseTime = System.currentTimeMillis() - startTime;
                Log.i(TAG, "req: " + responseTime + "ms responseCallback time");

                return response;
            }catch (Exception e){
                Log.e(TAG, "req: couldn't connect to URL", e);
                return new Response(EXCEPTION, e.toString().getBytes(), true);
            }
        } else {
            return new Response(NOT_VALID, null, true);
        }

        return new Response(INVALID_CONNECTION, null, true);
    }

    @Override
    public boolean isValid(){
        return validString(this.url) && validString(this.endpoint) && validString(this.body);
    }
}
