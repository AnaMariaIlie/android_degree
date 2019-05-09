package com.journaldev.barcodevisionapi;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.ConnectException;

public class BootRestClient {
    //"http://192.168.0.100:8080/interactionList";
    private static final String BASE_URL = "http://192.168.43.163:8080/interactionList";

    private static AsyncHttpClient client = new AsyncHttpClient();
    static {
        client.setMaxRetriesAndTimeout(1, 5000);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(BASE_URL, params, responseHandler);


    }

    public static void simplyGET(AsyncHttpResponseHandler responseHandler) {
        client.get(BASE_URL, responseHandler);

    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
