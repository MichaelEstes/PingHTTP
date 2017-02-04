package com.michaelestes.pinghttp;

/**
 * Created by michaelestes on 12/10/16.
 */

public interface RequestInterface {
    void req(ResponseCallback responseCallback);
    Response getResponse();
    boolean isValid();
}
