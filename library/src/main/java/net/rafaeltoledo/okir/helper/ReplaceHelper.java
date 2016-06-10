package net.rafaeltoledo.okir.helper;

import net.rafaeltoledo.okir.OkHttp3IdlingResource;
import net.rafaeltoledo.okir.OkHttpIdlingResource;

public final class ReplaceHelper {

    private ReplaceHelper() {
        // No instances!
    }

    public static okhttp3.OkHttpClient wrap(okhttp3.OkHttpClient client, OkHttp3IdlingResource idlingResource) {
        return client.newBuilder()
                .addInterceptor(idlingResource)
                .build();
    }

    public static com.squareup.okhttp.OkHttpClient wrap(com.squareup.okhttp.OkHttpClient client,
                                                        OkHttpIdlingResource idlingResource) {
        com.squareup.okhttp.OkHttpClient clone = client.clone();
        clone.interceptors().add(idlingResource);
        return clone;
    }
}
