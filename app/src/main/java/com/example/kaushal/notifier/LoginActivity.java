package com.example.kaushal.notifier;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

// to hide action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void Register(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
