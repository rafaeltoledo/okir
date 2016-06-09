package com.example.okir;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://api.github.com/repos/square/okhttp/commits";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = ApiCaller.getClient().newCall(new Request.Builder()
                            .url(URL).build()).execute();
                    final String text = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textView = new TextView(MainActivity.this);
                            textView.setId(android.R.id.text1);
                            textView.setText(text);
                            setContentView(textView);
                        }
                    });
                } catch (IOException e) {
                    Log.e("MainActivity", "Oops...", e);
                }
            }
        }).start();
    }
}
