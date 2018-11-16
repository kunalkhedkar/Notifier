package com.example.kaushal.notifier;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {
    EditText oldPassword, newPassword, confirmPassword;
    String sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Schedular");

        oldPassword = (EditText) findViewById(R.id.oldpassword);
        newPassword = (EditText) findViewById(R.id.newpassword);
        confirmPassword = (EditText) findViewById(R.id.confirmpassword);


    }

    public void updatePassword(View view) {

        //check for empty
        //check if (old.equals(sample) && new1.equals(confirm)) {

        //Log.d("TAG", "updatePassword "+ID);

        final String old = oldPassword.getText().toString();
        final String new1 = newPassword.getText().toString();
        final String confirm = confirmPassword.getText().toString();

        if (!old.isEmpty() && !new1.isEmpty() && !confirm.isEmpty()) {
            if (new1.equals(confirm)) {

                if(!old.equals(new1)) {

                    SharedPreferences pre = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
                    String ID = pre.getString("id", null);
                    final String myusername = pre.getString("username", null);

                    final DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("user");
                    Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Boolean flag=false;
                            //Log.d("TAG", "onDataChange:"+old);
                            for (DataSnapshot userDataSnapShot : dataSnapshot.getChildren()) {
                                Users u = userDataSnapShot.getValue(Users.class);
                                if ((myusername.equals(u.getU_Username())&&old.equals(u.getU_Password()))) {
                                    sample = u.getU_Password();
                                    u.setU_Password(new1);
                                    String id = userDataSnapShot.getKey();
                                    flag=true;
                                    Ref.child(id).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ChangePassword.this, "Successfully changed password", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                    break;
                                }


                            }
                            if(!flag)
                            oldPassword.setError("old password not correct");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }else
                    newPassword.setError("your old password can not be new password");

            } else
                confirmPassword.setError("Confirm Password Not Match");
        } else {
            if (old.isEmpty()) {
                oldPassword.setError("Enter current password");
                return;
            } else if (new1.isEmpty()) {
                newPassword.setError("Enter new password");
                return;
            } else if (confirm.isEmpty()) {
                confirmPassword.setError("Enter confirm password");
                return;
            }


        }

    }
}
