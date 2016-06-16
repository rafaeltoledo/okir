package net.rafaeltoledo.okir;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class OkHttpIdlingResource extends Okir implements Interceptor {

    public OkHttpIdlingResource(String... strictUrls) {
        super(strictUrls);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        processRequest(chain.request().httpUrl().url().toString());
        Response response = chain.proceed(chain.request());
        getCounter().decrementAndGet();
        return response;
    }

    @Override
    protected String getIdlingResourceName() {
        return OkHttpIdlingResource.class.getCanonicalName();
    }
}
