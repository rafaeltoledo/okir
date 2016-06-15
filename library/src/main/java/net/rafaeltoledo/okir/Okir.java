package net.rafaeltoledo.okir;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

abstract class Okir implements IdlingResource {
    private AtomicInteger busy = new AtomicInteger(0);
    private final String[] strictUrls;
    private ResourceCallback callback;

    Okir() {
        this.strictUrls = new String[0];
    }

    Okir(String... strictUrls) {
        this.strictUrls = strictUrls;
    }

    protected abstract String getIdlingResourceName();

    @Override
    public String getName() {
        return getIdlingResourceName();
    }

    @Override
    public boolean isIdleNow() {
        int currentValue = getCounter().get();
        if (currentValue == 0 && callback != null) {
            callback.onTransitionToIdle();
        }
        return currentValue == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    void processRequest(String requestUrl) {
        if (strictUrls.length != 0) {
            boolean busy = false;
            for (String url : strictUrls) {
                if (requestUrl.startsWith(url)) {
                    busy = true;
                }
            }
            getCounter().addAndGet(busy ? 1 : 0);
        } else {
            getCounter().incrementAndGet();
        }
    }

    AtomicInteger getCounter() {
        return busy;
    }
}
