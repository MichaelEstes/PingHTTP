package com.michaelestes.pinghttp;

/**
 * Created by michaelestes on 12/10/16.
 */

public interface ResponseCallback {
    void onSuccess(Response response);
    void onError(Response response);
}
