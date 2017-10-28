package com.example.kaushal.notifier;

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

public class ChangePassword extends AppCompatActivity {
    EditText oldPassword,newPassword,confirmPassword;
    String sample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword= (EditText) findViewById(R.id.oldpassword);
        newPassword= (EditText) findViewById(R.id.newpassword);
        confirmPassword= (EditText) findViewById(R.id.confirmpassword);



    }

    public void updatePassword(View view) {
        SharedPreferences pre=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        String ID=pre.getString("id",null);
        //Log.d("TAG", "updatePassword "+ID);

        final String old=oldPassword.getText().toString();
        final String new1=newPassword.getText().toString();
        final String confirm=confirmPassword.getText().toString();

        final DatabaseReference Ref= FirebaseDatabase.getInstance().getReference("user");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("TAG", "onDataChange:"+old);
                for (DataSnapshot userDataSnapShot : dataSnapshot.getChildren()) {
                    Users u = userDataSnapShot.getValue(Users.class);
                    sample = u.getU_Password();
                    if (old.equals(sample)&& new1.equals(confirm)) {                     //&& new1.equals(confirm)){
                            //Toast.makeText(ChangePassword.this, "value are same", Toast.LENGTH_SHORT).show();
                        u.setU_Password(new1);
                        break;
                    }
                    //Log.d("TAG", "onDataChange " + sample);

                    Ref.setValue(u);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
