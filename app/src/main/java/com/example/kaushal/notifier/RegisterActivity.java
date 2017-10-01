package com.example.kaushal.notifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerdone(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Register Sucessfully", Toast.LENGTH_SHORT).show();
    }


// spinner listener -----  https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event
    // how visible roll number.... RrollNumber.setVisbility(View.VISIBLE)
}
