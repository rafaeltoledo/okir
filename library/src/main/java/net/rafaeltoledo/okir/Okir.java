package net.rafaeltoledo.okir;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

abstract class Okir implements IdlingResource {

    private AtomicInteger busy = new AtomicInteger(0);
    final String[] strictUrls;
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
        int currentValue = CounterManager.getInstance().get();
        if (currentValue == 0 && callback != null) {
            callback.onTransitionToIdle();
        }
        return currentValue == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    protected AtomicInteger getCounter() {
        return busy;
    }
}
