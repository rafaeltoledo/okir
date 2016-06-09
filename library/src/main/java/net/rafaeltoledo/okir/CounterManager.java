package net.rafaeltoledo.okir;

import java.util.concurrent.atomic.AtomicInteger;

class CounterManager {
    private static AtomicInteger counter;

    private CounterManager() {
    }

    static AtomicInteger getInstance() {
        if (counter == null) {
            counter = new AtomicInteger(0);
        }
        return counter;
    }
}
