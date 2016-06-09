package net.rafaeltoledo.okir;

import android.support.test.espresso.IdlingResource;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Interceptor;
import okhttp3.Response;

public class OkHttp3IdlingResource implements Interceptor, IdlingResource {

    private final String[] strictUrls;
    private AtomicInteger busy = new AtomicInteger(0);
    private ResourceCallback callback;

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
            this.busy.addAndGet(busy ? 1 : 0);
        } else {
            busy.incrementAndGet();
        }

        Response response = chain.proceed(chain.request());
        busy.decrementAndGet();
        return response;
    }

    @Override
    public String getName() {
        return "net.rafaeltoledo.okhttpidlingresource.OkHttp3IdlingResource";
    }

    @Override
    public boolean isIdleNow() {
        if (busy.get() == 0 && callback != null) {
            callback.onTransitionToIdle();
        }
        return busy.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}
