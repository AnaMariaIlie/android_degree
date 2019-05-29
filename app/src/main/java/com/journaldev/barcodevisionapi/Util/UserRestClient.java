package com.journaldev.barcodevisionapi.Util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class UserRestClient {
    private static final String BASE_URL =  "http://192.168.0.101:8080/user/";//"http://192.168.43.163:8080/user/";

    private static AsyncHttpClient client = new AsyncHttpClient();
    static {
        client.setMaxRetriesAndTimeout(1, 5000);
    }

    public static void post(String id, AsyncHttpResponseHandler responseHandler) {
        client.post(BASE_URL + id, responseHandler);
    }

}

