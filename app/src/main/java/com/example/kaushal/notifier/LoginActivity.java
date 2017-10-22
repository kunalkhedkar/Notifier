package com.example.kaushal.notifier;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
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

        //is log in shared but not understand

//       SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
//       boolean b1=preferences.getBoolean("isLoggedIn",false);
//
//        if(b1==true){
//            Intent s_intent=new Intent(LoginActivity.this,StudentNaviActivity.class);
//            startActivity(s_intent);
//        }
//        if(b1==false){
//            Intent intent=new Intent(LoginActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }
    }

    public void Register(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
