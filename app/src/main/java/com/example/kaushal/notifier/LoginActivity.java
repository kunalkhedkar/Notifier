package com.example.kaushal.notifier;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference USER_REFEENCE;
    EditText user, pass;
    String user1, pass1;
    String role;
    Boolean flag = false;
    String sample;
    ProgressBar progressBar;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        progressBar= (ProgressBar) findViewById(R.id.ProgressBar);
        Login= (Button) findViewById(R.id.Login);
        Login.setVisibility(View.VISIBLE);
        // url
        USER_REFEENCE = FirebaseDatabase.getInstance().getReference("user");
// to hide action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        String tid=getIntent().getStringExtra("tid");
        if(tid!=null) {
            Toast.makeText(this, "login "+tid, Toast.LENGTH_SHORT).show();
            FirebaseMessaging.getInstance().subscribeToTopic(tid);
        }

        //SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        //haredPreferences.Editor editor = preferences.edit();
        //editor.putString("username",null);


        //is login shared but not understand
//
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
        finish();
//        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Login.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void Register(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Login.setVisibility(View.GONE);

        final String User = user.getText().toString();
        final String Pass = pass.getText().toString();

        if (!User.isEmpty() && !Pass.isEmpty()) {

            USER_REFEENCE.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                        Users uu = userDataSnapshot.getValue(Users.class);
                        user1 = uu.getU_Username().toString();
                        pass1 = uu.getU_Password().toString();
                        role = uu.getU_Role().toString();
                        String ID = uu.getU_ID();


                        if (User.equalsIgnoreCase(user1) && Pass.equalsIgnoreCase(pass1)) {
                            SharedPreferences sharedPreferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                        String id=sharedPreferences.getString("id",null);
                            //Log.d("TAG", "SharedID:"+ID);

                            editor.putBoolean("isLoggedIn", true);
                            // add islogged value to shared editor

//                            UnSubscribeTopic.unSubscribe(LoginActivity.this);
//                            unSubscribe();

                            if (role.equalsIgnoreCase("student")) {
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("role", "student");
                                editor.putString("username", user1);
                                editor.putString("id", ID);
                                //editor.putString("classType",null);
                                editor.commit();

                                registerForNotification(uu.getU_ID());

                                flag = true;

                                Intent intent = new Intent(LoginActivity.this, StudentNaviActivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                                finish();
                                break;
                            } else if (role.equalsIgnoreCase("teacher")) {
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("role", "teacher");
                                editor.putString("username", user1);
                                editor.putString("id", ID);
                                editor.commit();

                                flag = true;
                                String teacherUsername=uu.getU_Username();
                                teacherUsername=teacherUsername.replace("@","_");
                                teacherUsername=teacherUsername.replace(".","_");
                                Log.d("TAG", "onDataChange: sub "+teacherUsername);
                                FirebaseMessaging.getInstance().subscribeToTopic(teacherUsername);
                                FirebaseMessaging.getInstance().subscribeToTopic("teacher");
                                Log.d("TAG", "onDataChange: Teacher subscribeToTopic "+uu.getU_ID());
                                Intent intent = new Intent(LoginActivity.this, TeacherNaviActivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                                finish();
                                break;

                            } else if (role.equalsIgnoreCase("head")) {
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("role", "head");
                                editor.putString("username", user1);
                                editor.putString("id", ID);
                                editor.commit();
                                flag = true;

//                                FirebaseMessaging.getInstance().unsubscribeFromTopic("head");
                                FirebaseMessaging.getInstance().subscribeToTopic("head");
                                Toast.makeText(LoginActivity.this, "sub to head2", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, HeadNaviActivity.class);
                                startActivity(intent);
                                finish();
                                break;

                            }
//                        else if(!User.equals(user1) && !Pass.equals(pass1)){
////                            user.setError("please check username and password");
////                            break;
//
//                        }
                        }

                    }
                    if (flag == false) {
                        Log.d("TAG", "onDataChange: fun " + validLogin());

                        if (!validLogin()) {
                            user.setError("please check username or password");
                            Login.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else{
            user.setError("Field can not be empty");
            Login.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }




    }

    private void registerForNotification(final String id) {
        DatabaseReference STUDENT_REFERENCE = FirebaseDatabase.getInstance().getReference("student");
        STUDENT_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                    Student ss=userDataSnapshot.getValue(Student.class);
                    if(ss.getS_ID().equals(id)) {
                        FirebaseMessaging.getInstance().subscribeToTopic("student");
                        FirebaseMessaging.getInstance().subscribeToTopic(ss.getS_Class());
                        FirebaseMessaging.getInstance().subscribeToTopic(id);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public boolean validLogin() {

//        SharedPreferences pre = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
//        final String check = pre.getString("username", null);
//        Log.d("TAG", "validLogin: user "+check);
        final String[] dbTeacher = new String[1];
        final String check = user.getText().toString();
        Log.d("TAG", "validLogin: user " + check);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("teacher");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Teacher u1 = userDataSnapshot.getValue(Teacher.class);
                    sample = u1.getT_Username();
                    if (check.equals(sample)) {
                        Toast.makeText(LoginActivity.this, "Login is Not Aproved by head", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
                Log.d("TAG", "validLogin: sample last " + sample);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (check.equals(sample))
            return true;

        return false;
    }
}