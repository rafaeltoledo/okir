package com.example.okir;

import android.support.annotation.VisibleForTesting;

import okhttp3.OkHttpClient;

public final class ApiCaller {

    private static OkHttpClient client = new OkHttpClient();

    private ApiCaller() {
        // No instances!
    }

    public static OkHttpClient getClient() {
        return client;
    }

    @VisibleForTesting
    public static void setClient(OkHttpClient client) {
        ApiCaller.client = client;
    }
}
