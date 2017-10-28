package com.example.kaushal.notifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private final int SPALSH_SCREEN_TIME_OUT = 2 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // to hide action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String s1;

                SharedPreferences preferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
                s1 = preferences.getString("role", null);
                boolean logInFlag = preferences.getBoolean("isLoggedIn", false);
                Log.d("TAG", "login status " + logInFlag);

                if (s1.equalsIgnoreCase("student")) {

                    if (logInFlag == true) {
                        Intent intent = new Intent(SplashActivity.this, StudentNaviActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else if (s1.equalsIgnoreCase("teacher")) {

                    if (logInFlag == true) {
                        Intent intent = new Intent(SplashActivity.this, TeacherNaviActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                // head
                else if (s1.equalsIgnoreCase("head")) {

                    if (logInFlag == true) {
                        Intent intent = new Intent(SplashActivity.this, HeadNaviActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        }, SPALSH_SCREEN_TIME_OUT);
    }


    // read
//    SharedPreferences prefs = getSharedPreferences(PREF_FILE_NAME,MODE_PRIVATE);
//    String length = prefs.getString("length", "deafalut value");

}
