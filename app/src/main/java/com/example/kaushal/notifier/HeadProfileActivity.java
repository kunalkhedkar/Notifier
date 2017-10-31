package com.example.kaushal.notifier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HeadProfileActivity extends AppCompatActivity {
    EditText username, fullname, institutename;
    String sfullname, sinstitutename;
    DatabaseReference HEAD_REF;
    private String sharedusername;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_profile);

        username = (EditText) findViewById(R.id.headUsername);
        fullname = (EditText) findViewById(R.id.headName);
        institutename = (EditText) findViewById(R.id.InstituteName);
        progressBar= (ProgressBar) findViewById(R.id.ProgressBar);

        HEAD_REF = FirebaseDatabase.getInstance().getReference("head");

        SharedPreferences preferences = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
        String ID = preferences.getString("id", null);
        sharedusername = preferences.getString("username", null);
        // Log.d("TAG", "onCreate:"+sharedusername);

        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("head");
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapShot : dataSnapshot.getChildren()) {
                    Head u = userDataSnapShot.getValue(Head.class);
                    if (sharedusername.equals(u.getHeadUsername())) {
                        sfullname = u.getFullName();
                        sinstitutename = u.getInstituteName();
                        break;
                    }

                }
                //Log.d("TAG", "onDataChange: "+setusername);
                username.setText(sharedusername);
                if (sfullname != null)
                    fullname.setText(sfullname);


                if (sinstitutename != null)
                    institutename.setText(sinstitutename);

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

        //check for empty edittext
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        final String ID=preferences.getString("id",null);

        String headUsername=username.getText().toString();
        final String fullName=fullname.getText().toString();
        final String headInstituteName=institutename.getText().toString();

//        String h_ID = HEAD_REF.push().getKey();
        Head h1=new Head(headUsername,fullName,headInstituteName);
        HEAD_REF.child(ID).setValue(h1);

        final String newUsername=username.getText().toString();
        if(!newUsername.equals(sharedusername)){

            final DatabaseReference userRef=FirebaseDatabase.getInstance().getReference("user");

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot userDataSnapShot:dataSnapshot.getChildren()){
                        Users u=userDataSnapShot.getValue(Users.class);
                        if(ID.equals(u.getU_ID())){
                            u.setU_Username(newUsername);
                            userRef.child(userDataSnapShot.getKey()).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    SharedPreferences pref=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
                                    SharedPreferences.Editor editor=pref.edit();
                                    editor.putString("username",newUsername);
                                    editor.commit();
                                    username.setText(newUsername);
                                    fullname.setText(fullName);
                                    institutename.setText(headInstituteName);
                                    Toast.makeText(HeadProfileActivity.this, "Successfully saved..!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });


        }

        progressBar.setVisibility(View.GONE);
    }

    public void changePassword(View view) {
        Intent it=new Intent(HeadProfileActivity.this,ChangePassword.class);
        startActivity(it);

    }

}
