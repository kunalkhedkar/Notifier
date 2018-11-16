package com.example.kaushal.notifier;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangPasswordFragment extends Fragment {
    EditText oldPassword, newPassword, confirmPassword;
    Button changePassword;
    String sample;
    View view;


    public ChangPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_chang_password, container, false);
        oldPassword = (EditText)view.findViewById(R.id.oldpassword);
        newPassword = (EditText) view.findViewById(R.id.newpassword);
        confirmPassword = (EditText)view.findViewById(R.id.confirmpassword);
        changePassword=(Button) view.findViewById(R.id.changepassword);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();

            }
        });
        return  view;

    }
    public void updatePassword() {

        //check for empty
        //check if (old.equals(sample) && new1.equals(confirm)) {

        //Log.d("TAG", "updatePassword "+ID);

        final String old = oldPassword.getText().toString();
        final String new1 = newPassword.getText().toString();
        final String confirm = confirmPassword.getText().toString();

        if (!old.isEmpty() && !new1.isEmpty() && !confirm.isEmpty()) {
            if (new1.equals(confirm)) {

                if(!old.equals(new1)) {

                    SharedPreferences pre = getActivity().getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
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
                                            Toast.makeText(getActivity(), "Successfully changed password", Toast.LENGTH_SHORT).show();

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
