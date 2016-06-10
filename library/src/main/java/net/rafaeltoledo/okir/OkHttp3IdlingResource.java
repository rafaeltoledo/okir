package net.rafaeltoledo.okir;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class OkHttp3IdlingResource extends Okir implements Interceptor {
    private final String[] strictUrls;

    public OkHttp3IdlingResource() {
        this.strictUrls = new String[0];
    }

    public OkHttp3IdlingResource(String... strictUrls) {
        this.strictUrls = strictUrls;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (strictUrls.length != 0) {
            boolean busy = false;
            for (String url : strictUrls) {
                if (chain.request().url().toString().startsWith(url)) {
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
        return OkHttp3IdlingResource.class.getCanonicalName();
    }
}
