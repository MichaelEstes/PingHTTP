package com.michaelestes.pinghttp;

/**
 * Created by michaelestes on 12/10/16.
 */

public class Response {
    public int responseCode = -1;
    public byte[] responseBody = null;
    public boolean error = true;

    public Response(int responseCode, byte[] responseBody, boolean error){
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.error = error;
    }

    public Response(){}
}
