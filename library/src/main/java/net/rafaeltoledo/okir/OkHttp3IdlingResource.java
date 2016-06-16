package net.rafaeltoledo.okir;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class OkHttp3IdlingResource extends Okir implements Interceptor {

    public OkHttp3IdlingResource(String... strictUrls) {
        super(strictUrls);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        processRequest(chain.request().url().toString());
        Response response = chain.proceed(chain.request());
        getCounter().decrementAndGet();
        return response;
    }

    @Override
    protected String getIdlingResourceName() {
        return OkHttp3IdlingResource.class.getCanonicalName();
    }
}
