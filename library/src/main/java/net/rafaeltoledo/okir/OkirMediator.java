package net.rafaeltoledo.okir;

import java.io.IOException;

class OkirMediator {
    static void processRequest(String requestUrl, String[] strictUrls) throws IOException {
        if (strictUrls.length != 0) {
            boolean busy = false;
            for (String url : strictUrls) {
                if (requestUrl.startsWith(url)) {
                    busy = true;
                }
            }
            CounterManager.getInstance().addAndGet(busy ? 1 : 0);
        } else {
            CounterManager.getInstance().incrementAndGet();
        }

        CounterManager.getInstance().decrementAndGet();
    }
}
