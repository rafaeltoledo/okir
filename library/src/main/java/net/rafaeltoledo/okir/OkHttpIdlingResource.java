package net.rafaeltoledo.okir;

import android.support.test.espresso.IdlingResource;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class OkHttpIdlingResource implements Interceptor, IdlingResource {

    private final String[] strictUrls;
    private AtomicInteger busy = new AtomicInteger(0);
    private ResourceCallback callback;

    public OkHttpIdlingResource() {
        this.strictUrls = new String[0];
    }

    public OkHttpIdlingResource(String... strictUrls) {
        this.strictUrls = strictUrls;
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
        return "net.rafaeltoledo.okhttpidlingresource.OkHttpIdlingResource";
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
