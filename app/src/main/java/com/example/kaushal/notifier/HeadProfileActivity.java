package com.example.kaushal.notifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HeadProfileActivity extends AppCompatActivity {
    EditText username,fullname,institutename;
    String setusername;
    DatabaseReference HEAD_REF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_profile);

        username= (EditText) findViewById(R.id.headUsername);
        fullname= (EditText) findViewById(R.id.headName);
        institutename= (EditText) findViewById(R.id.InstituteName);
        HEAD_REF=FirebaseDatabase.getInstance().getReference("head");

        SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        String ID=preferences.getString("id",null);
        final String sharedusername=preferences.getString("username",null);
       // Log.d("TAG", "onCreate:"+sharedusername);

        DatabaseReference Ref= FirebaseDatabase.getInstance().getReference("user");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDataSnapShot:dataSnapshot.getChildren()){
                    Users u=userDataSnapShot.getValue(Users.class);
                    if(sharedusername.equals(u.getU_Username())){
                        setusername=u.getU_Username();
                        break;
                    }

                }
                //Log.d("TAG", "onDataChange: "+setusername);
                  username.setText(setusername);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        //String sample=preferences.getString("username",null);
        //username.setText(sample);
    }

    public void save(View view) {

        String headUsername=username.getText().toString();
        String fullName=fullname.getText().toString();
        String headInstituteName=institutename.getText().toString();

        String h_ID = HEAD_REF.push().getKey();
        Head h1=new Head(headUsername,fullName,headInstituteName);
        HEAD_REF.child(h_ID).setValue(h1);





    }

    public void changePassword(View view) {
        Intent it=new Intent(HeadProfileActivity.this,ChangePassword.class);
        startActivity(it);

    }
}
