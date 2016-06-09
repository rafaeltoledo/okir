package net.rafaeltoledo.okir;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

abstract class Okir implements IdlingResource {

    AtomicInteger busy = new AtomicInteger(0);
    private ResourceCallback callback;

    protected abstract String getIdlingResourceName();

    @Override
    public String getName() {
        return getIdlingResourceName();
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
