package com.example.okir;

import android.support.annotation.VisibleForTesting;

import okhttp3.OkHttpClient;

public class ApiCaller {

    private static OkHttpClient CLIENT = new OkHttpClient();

    public static OkHttpClient getClient() {
        return CLIENT;
    }

    @VisibleForTesting
    public static void setClient(OkHttpClient client) {
        CLIENT = client;
    }
}
