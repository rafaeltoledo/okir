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
        if (strictUrls.length != 0) {
            boolean busy = false;
            for (String url : strictUrls) {
                if (chain.request().httpUrl().url().toString().startsWith(url)) {
                    busy = true;
                }
            }
            getCounter().addAndGet(busy ? 1 : 0);
        } else {
            getCounter().incrementAndGet();
        }

        Response response = chain.proceed(chain.request());
        getCounter().decrementAndGet();
        return response;
    }

    @Override
    protected String getIdlingResourceName() {
        return OkHttpIdlingResource.class.getCanonicalName();
    }
}
