package com.example.okir;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.rafaeltoledo.okir.OkHttp3IdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MyActivityMockTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    private MockWebServer server;
    private OkHttp3IdlingResource idlingResource = new OkHttp3IdlingResource();

    @Before
    public void registerIdlingResource() throws Exception {
        server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("<html>Hello</html>"));


        Espresso.registerIdlingResources(idlingResource);
        OkHttpClient client = ApiCaller.getClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        return chain.proceed(
                                request.newBuilder()
                                        .url(request.url().toString().replace("https://google.com",
                                                server.url("/").toString()))
                                        .build());
                    }
                })
                .addInterceptor(idlingResource)
                .build();
        ApiCaller.setClient(client);
    }

    @After
    public void unregisterIdlingResource() throws IOException {
        server.shutdown();
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void checkIfTextIsDisplayed() {
        activityRule.launchActivity(new Intent(Intent.ACTION_MAIN));

        onView(withText("<html>Hello</html>")).check(matches(isDisplayed()));
    }
}
