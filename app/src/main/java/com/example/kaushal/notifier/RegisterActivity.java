package com.example.kaushal.notifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference STUDENT_REFERENCE;
    DatabaseReference DUMMY_TEACHER_REFERENCE;
    DatabaseReference USER_REFERENCE;
    String token="token";



    EditText Rname,Rusername,Rpassword,Rmobile,Radress,RroleNumber;
    Spinner Rclass,Rrole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        Rname= (EditText) findViewById(R.id.Rname);
        Rusername= (EditText) findViewById(R.id.Remail);
        Rpassword= (EditText) findViewById(R.id.Rpassword);
        Rmobile= (EditText) findViewById(R.id.Rmobile);
        Radress= (EditText) findViewById(R.id.Raddres);
        RroleNumber= (EditText) findViewById(R.id.RroleNumber);
        Rclass= (Spinner) findViewById(R.id.Rclass);
        Rrole= (Spinner) findViewById(R.id.Rrole);

        // role and class spinner
        String classs[]=getResources().getStringArray(R.array.class_types);
        String role[]=getResources().getStringArray(R.array.role_types);

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

    }

    public void registerOnClick(View view) {

            //All value converted into string

        String name=Rname.getText().toString();
        String username=Rusername.getText().toString();
        String password=Rpassword.getText().toString();
        String mobile=Rmobile.getText().toString();
        String address=Radress.getText().toString();
        String RollNumber=RroleNumber.getText().toString();

        String classType=Rclass.getSelectedItem().toString();
        String roleType=Rrole.getSelectedItem().toString();


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

        if(mobile.length()!=10)
        {
            Rmobile.setError("please enter valid number");
            return;
        }
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


        if(name.isEmpty())
        {
            Rname.setError("Cant be empty");
            return;
        }

        if(username.isEmpty())
        {
            Rusername.setError("Can't be empty");
            return;
        }

        if(password.isEmpty())
        {
            Rpassword.setError("Cant be empty");
            return;
         }
        if(RollNumber.isEmpty())
        {
            RroleNumber.setError("Cant be empty");
            return;
        }
        if(mobile.isEmpty())
        {
            Rmobile.setError("Cant be empty");
            return;
        }
        if(address.isEmpty()) {
            Radress.setError("Cant be empty");
            return;
        }


        if(roleType.equals("student")) {

            String ID = STUDENT_REFERENCE.push().getKey();
            Log.d("TAG", "registerOnClick:" + ID);
            Student s1 = new Student(ID, name, username, password, RollNumber, mobile, address, roleType, classType);
            STUDENT_REFERENCE.child(ID).setValue(s1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(RegisterActivity.this, "Register Sucessfully", Toast.LENGTH_SHORT).show();
                }

            });
            //read
            STUDENT_REFERENCE.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                        Student ss = userDataSnapshot.getValue(Student.class);

                        Log.d("TAG", ss.getS_Name() + "\t" + ss.gets_Username() + "\t" + ss.getS_Address() + "\t" + ss.getS_Password() + "\t" + ss.getS_Mobile() + "\t" + ss.getS_Role() + "\t" + ss.getS_Class() + "\t" + ss.gets_RollNumber());

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            acceptUser(ID,username,password,roleType,token);
            //add to shared
            SharedPreferences preferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id",ID);
            editor.putString("role",roleType);
            editor.putString("username",username);
            editor.putBoolean("isLoggedIn",true);
            editor.apply();

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
            String t_ID=DUMMY_TEACHER_REFERENCE.push().getKey();
            Log.d("TAG", "registerOnClick:" + t_ID);
            Teacher tt=new Teacher(t_ID,name,username,password,roleType,mobile,address);
            DUMMY_TEACHER_REFERENCE.child(t_ID).setValue(tt);

            DUMMY_TEACHER_REFERENCE.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot usersDataSnapshot:dataSnapshot.getChildren()){
                        Teacher t1=usersDataSnapshot.getValue(Teacher.class);
                        Log.d("TAG",t1.getT_Name()+"\t"+t1.getT_Username()+"\t"+t1.getT_Password()+"\t"+t1.getT_Role()+"\t"+t1.getT_Mobile()+"\t"+t1.getT_Address());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
           // Toast.makeText(this, ""+roleType, Toast.LENGTH_SHORT).show();
            acceptUser(t_ID,username,password,roleType,token);

            SharedPreferences preferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id",t_ID);
            editor.putString("role",roleType);
            editor.putString("username",username);
            editor.putBoolean("isLoggedIn",true);
            editor.apply();
        }



        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }


    public void acceptUser(String u_ID,String username,String password,String role,String token){
        String ID=USER_REFERENCE.push().getKey();
        Users uu=new Users(u_ID,username,password,role,token);
        USER_REFERENCE.child(ID).setValue(uu);

        USER_REFERENCE.child(ID).setValue(uu);

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


    }











// spinner listener -----  https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event
    // how visible roll number.... RrollNumber.setVisbility(View.VISIBLE)

    // add another spinner for edu_type- bcs msc
}
