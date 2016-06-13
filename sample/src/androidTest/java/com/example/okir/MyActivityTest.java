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

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MyActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    private OkHttp3IdlingResource idlingResource = new OkHttp3IdlingResource();

    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(idlingResource);
        OkHttpClient client = ApiCaller.getClient().newBuilder()
                .addInterceptor(idlingResource)
                .build();
        ApiCaller.setClient(client);
    }

    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void checkIfTextIsDisplayed() {
        activityRule.launchActivity(new Intent(Intent.ACTION_MAIN));

        onView(withId(android.R.id.text1)).check(matches(isDisplayed()));
    }
}
