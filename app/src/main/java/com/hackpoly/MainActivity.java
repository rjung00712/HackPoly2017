package com.hackpoly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(this, Gps.class));

        if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
            startActivity(new Intent(this, Login.class));
        } else {
            startActivity(new Intent(this, UserActivity.class));
        }
    }
}
