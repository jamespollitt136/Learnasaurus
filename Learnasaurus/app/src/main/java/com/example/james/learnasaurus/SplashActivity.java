package com.example.james.learnasaurus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        // launch new activity
        Intent mainIntent = new Intent(this, LoginActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
