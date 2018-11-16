package com.example.kaushal.notifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference STUDENT_REFERENCE;
    DatabaseReference DUMMY_TEACHER_REFERENCE;
    DatabaseReference USER_REFERENCE;
    String token="token";
    String tid;
    Button Register;


    EditText Rname,Rusername,Rpassword,Rmobile,Radress,RroleNumber;
    Spinner Rclass,Rrole;
    ProgressBar progressBar;
    private String username;
    private boolean[] flag;
    ArrayList<String> existUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        existUsername=new ArrayList<>();

        Rname= (EditText) findViewById(R.id.Rname);
        Rusername= (EditText) findViewById(R.id.Remail);
        Rpassword= (EditText) findViewById(R.id.Rpassword);
        Rmobile= (EditText) findViewById(R.id.Rmobile);
        Radress= (EditText) findViewById(R.id.Raddres);
        RroleNumber= (EditText) findViewById(R.id.RroleNumber);
        Rclass= (Spinner) findViewById(R.id.Rclass);
        Rrole= (Spinner) findViewById(R.id.Rrole);
        Register= (Button) findViewById(R.id.Register);
        progressBar= (ProgressBar) findViewById(R.id.ProgressBar);
        // role and class spinner
        String classs[]=getResources().getStringArray(R.array.class_types);
        final String role[]=getResources().getStringArray(R.array.role_types);

        //role and class adapter

        ArrayAdapter<String> adapter_class = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,classs);
        Rclass.setAdapter(adapter_class);
        ArrayAdapter<String> adapter_role=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,role);
        Rrole.setAdapter(adapter_role);

        // firebase url
        USER_REFERENCE=FirebaseDatabase.getInstance().getReference("user");
        STUDENT_REFERENCE= FirebaseDatabase.getInstance().getReference("student");
        DUMMY_TEACHER_REFERENCE=FirebaseDatabase.getInstance().getReference("teacher");
//            String shared_fileName=MyConstant.SHARED_FILE;


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        // add listerner to spinner for visibility

        Rrole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedRole=role[i];
                if(selectedRole.equals("teacher")){
                    RroleNumber.setVisibility(View.GONE);
                    Rclass.setVisibility(View.GONE);
                }else
                {
                    RroleNumber.setVisibility(View.VISIBLE);
                    Rclass.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }




    public void registerOnClick(View view) {

        Register.setClickable(true);

        //All value converted into string
        progressBar.setVisibility(View.VISIBLE);
        final String name = Rname.getText().toString();
        username = Rusername.getText().toString();
        final String password = Rpassword.getText().toString();
        final String mobile = Rmobile.getText().toString();
        final String address = Radress.getText().toString();
        final String RollNumber = RroleNumber.getText().toString();

        final String classType = Rclass.getSelectedItem().toString();
        final String roleType = Rrole.getSelectedItem().toString();


       /* if(name.isEmpty()){
            Rname.setError("can not be empty");
            return;
        }*/


//        Role.equals("student");
        //validation - not empty

//
//        public String s_ID;
//        public String s_Name;
//        public String s_Email;
//        public String s_Password;
//        public String s_RoleNumber;
//        public String s_Mobile;
//        public String s_Address;
//        public String s_Role;
//        public String s_Class;

//        if(name.isEmpty()){
//            Rname.setError("can't be empty");
//            return;
//        }

//        if(!mobile.startsWith("9")){
//            Rmobile.setError("please enter valid number");
//            return;
//        }
//        if(!mobile.startsWith("7")){
//            Rmobile.setError("please enter valid number");
//            return;
//        }
//        if(!mobile.startsWith("8")) {
//            Rmobile.setError("please enter valid number");
//            return;
//        }


        if (name.isEmpty()) {
            Rname.setError("Cant be empty");
            return;
        }

        if (username.isEmpty()) {
            Rusername.setError("Can't be empty");
            return;
        }
        if (!username.contains("@")) {
            Rusername.setError("Not correct");
            return;
        }

        if (password.isEmpty()) {
            Rpassword.setError("Cant be empty");
            return;
        }
        if (mobile.length() != 10) {
            Rmobile.setError("please enter valid number");
            return;
        }

        if (mobile.isEmpty()) {
            Rmobile.setError("Cant be empty");
            return;
        }
        if (address.isEmpty()) {
            Radress.setError("Cant be empty");
            return;
        }

        // serching for existing
        USER_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener() {

            boolean flag = false;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot usersDataSnapshot : dataSnapshot.getChildren()) {
                    Users u1 = usersDataSnapshot.getValue(Users.class);
                    if (username.equals(u1.getU_Username())) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {


                    if(roleType.equals("student")) {

                        if(roleType.equals("select role")){
                            Toast.makeText(getApplicationContext(), "please Select Role", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(RollNumber.isEmpty())
                        {
                            RroleNumber.setError("Cant be empty");
                            return;
                        }
                        if(classType.equals("select class")){
                            Toast.makeText(getApplicationContext(), "please Select class", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        final String ID = STUDENT_REFERENCE.push().getKey();
                        Log.d("TAG", "registerOnClick:" + ID);
                        Student s1 = new Student(ID, name, username, password, RollNumber, mobile, address, roleType, classType);
                        STUDENT_REFERENCE.child(ID).setValue(s1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "Register Sucessfully", Toast.LENGTH_SHORT).show();
                                FirebaseMessaging.getInstance().subscribeToTopic(ID);
                                //FirebaseMessaging.getInstance().subscribeToTopic("student");

                            }

                        });
                        //read

//            STUDENT_REFERENCE.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
//                        Student ss = userDataSnapshot.getValue(Student.class);
//                        Toast.makeText(RegisterActivity.this, "Registration Sucessfully", Toast.LENGTH_SHORT).show();
//
//                        Log.d("TAG", ss.getS_Name() + "\t" + ss.gets_Username() + "\t" + ss.getS_Address() + "\t" + ss.getS_Password() + "\t" + ss.getS_Mobile() + "\t" + ss.getS_Role() + "\t" + ss.getS_Class() + "\t" + ss.gets_RollNumber());
//
//                    }

//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
                        acceptUser(ID,username,password,roleType,token);
                        //add to shared
                        SharedPreferences preferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("id",ID);
//            editor.putString("role",roleType);
//            editor.putString("username",username);
//            editor.putBoolean("isLoggedIn",true);
//            //editor.putString("name",name);
//            editor.apply();
//
//            Intent intent=new Intent(this,LoginActivity.class);
//            startActivity(intent);

                        editor.putString("classType",classType);
                        editor.commit();
                        Log.d("TAG", "classType:"+classType );
                        progressBar.setVisibility(View.GONE);
                    }
        /*
        this.t_ID = t_ID;
        this.t_Name = t_Name;
        this.t_Username = t_Username;
        this.t_Password = t_Password;
        this.t_Role = t_Role;
        this.t_Mobile = t_Mobile;
        this.t_Address = t_Address;
    }
        */
                    // TEACHER

                    else{


                        final String t_ID=DUMMY_TEACHER_REFERENCE.push().getKey();
                        tid=t_ID;
                        Log.d("TAG", "registerOnClick:" + t_ID);
                        Teacher tt=new Teacher(t_ID,name,username,password,roleType,mobile,address);
                        final String title="New Teacher register";
                        final String msg=username+" has been newly register";
                        DUMMY_TEACHER_REFERENCE.child(t_ID).setValue(tt).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "Registration Sucessfully", Toast.LENGTH_SHORT).show();
                                FirebaseMessaging.getInstance().subscribeToTopic(t_ID);
                                CreateNotification createNotification=new CreateNotification(RegisterActivity.this);
                                createNotification.sendNotificationTopic(title,msg,"head");
                            }
                        });



                    }

                    //---------

                    SharedPreferences preferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("id");
                    editor.remove("role");
                    editor.remove("username");
                    editor.remove("isLoggedIn");
                    //editor.putString("name",name);
                    editor.commit();

                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                    intent.putExtra("tid",tid);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                }
                else
                    Toast.makeText(RegisterActivity.this, "Username already taken, please try with another email", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Register.setClickable(false);
    }

    public void acceptUser(String u_ID,String username,String password,String role,String token){
        String ID=USER_REFERENCE.push().getKey();
        Users uu=new Users(u_ID,username,password,role,token);
        USER_REFERENCE.child(ID).setValue(uu);

//        USER_REFERENCE.child(ID).setValue(uu);

        USER_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot usersDataSnapshot: dataSnapshot.getChildren()){
                    Users u1=usersDataSnapshot.getValue(Users.class);
                    Log.d("TAG",u1.getU_ID()+"\t"+u1.getU_Username()+"\t"+u1.getU_Password()+"\t"+u1.getU_Role()+"\t"+u1.getU_Token());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        SharedPreferences preferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("id",ID);
//        editor.putString("role",roleType);
//        editor.putString("username",username);
//        editor.putBoolean("isLoggedIn",true);
//        editor.apply();


    }


    // spinner listener -----  https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event
    // how visible roll number.... RrollNumber.setVisbility(View.VISIBLE)

    // add another spinner for edu_type- bcs msc
}
