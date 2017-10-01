package com.example.kaushal.notifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference STUDENT_REFERENCE;
    DatabaseReference TEACHER_REFERENCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        STUDENT_REFERENCE= FirebaseDatabase.getInstance().getReference("demo");

    }

    public void registerOnClick(View view) {

        //validation - not empty


        String ID=STUDENT_REFERENCE.push().getKey();
        STUDENT_REFERENCE.child(ID).setValue("kunal");
        //

        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Register Sucessfully", Toast.LENGTH_SHORT).show();
    }


// spinner listener -----  https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event
    // how visible roll number.... RrollNumber.setVisbility(View.VISIBLE)

    // add another spinner for edu_type- bcs msc
}
