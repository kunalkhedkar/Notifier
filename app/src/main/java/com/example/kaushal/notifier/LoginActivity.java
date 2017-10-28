package com.example.kaushal.notifier;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference USER_REFEENCE;
    EditText user, pass;
    String user1, pass1;
    String role;
    Boolean flag = false;
    String sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        // url
        USER_REFEENCE = FirebaseDatabase.getInstance().getReference("user");
// to hide action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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

    public void Register(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        final String User = user.getText().toString();
        final String Pass = pass.getText().toString();


        USER_REFEENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Users uu = userDataSnapshot.getValue(Users.class);
                    user1 = uu.getU_Username().toString();
                    pass1 = uu.getU_Password().toString();
                    role = uu.getU_Role().toString();
                    String ID=uu.getU_ID();




                    if (User.equalsIgnoreCase(user1) && Pass.equalsIgnoreCase(pass1)) {
                        SharedPreferences sharedPreferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        String id=sharedPreferences.getString("id",null);
                        //Log.d("TAG", "SharedID:"+ID);

                        editor.putBoolean("isLoggedIn", true);
                        // add islogged value to shared editor

                        if (role.equalsIgnoreCase("student")) {
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("role","student");
                            editor.putString("username",user1);
                            editor.putString("id",ID);
                            editor.commit();
                            flag = true;
                            Intent intent = new Intent(LoginActivity.this, StudentNaviActivity.class);
                            startActivity(intent);
                            break;
                        } else if (role.equalsIgnoreCase("teacher")) {
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("role","teacher");
                            editor.putString("username",user1);
                            editor.putString("id",ID);
                            editor.commit();
                            flag = true;
                            Intent intent = new Intent(LoginActivity.this, TeacherNaviActivity.class);
                            startActivity(intent);
                            break;

                        } else if (role.equalsIgnoreCase("head")) {
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("role","head");
                            editor.putString("username",user1);
                            editor.putString("id",ID);
                            editor.commit();
                            flag = true;
                            Intent intent = new Intent(LoginActivity.this, HeadNaviActivity.class);
                            startActivity(intent);
                            break;

                        }
                        ////                        else if(!User.equals(user1) && !Pass.equals(pass1)){
////                            user.setError("please check username and password");
////                            break;
//
//                        }
                    }

                }
                if (flag == false)
                    user.setError("please check username or password");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void validLogin(){

        SharedPreferences pre=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        final String check =pre.getString("username",null);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
         ref.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                 Users u1=userDataSnapshot.getValue(Users.class);
                 sample=u1.getU_Username();
                 if(!check.equals(sample)){
                     Toast.makeText(LoginActivity.this, "Login is Not Aproved by head", Toast.LENGTH_SHORT).show();
                     return;
                 }

             }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
    }
}
