package com.example.kaushal.notifier;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
public class HeadProfileFragment extends Fragment {
    EditText username, fullname, institutename;
    String sfullname, sinstitutename;
    DatabaseReference HEAD_REF;
    private String sharedusername;
    ProgressBar progressBar;
    View view;
    Button save,savepassword;


    public HeadProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_head_profile, container, false);

        username = (EditText) view.findViewById(R.id.headUsername);
        fullname = (EditText) view.findViewById(R.id.headName);
        institutename = (EditText) view.findViewById(R.id.InstituteName);
        progressBar= (ProgressBar) view.findViewById(R.id.ProgressBar);
        save=(Button) view.findViewById(R.id.save);
        savepassword=(Button) view.findViewById(R.id.changePassword);

        HEAD_REF = FirebaseDatabase.getInstance().getReference("head");

        SharedPreferences preferences = getActivity().getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
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

    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            save();
        }
    });
    savepassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changePassword();
        }
    });



        return view;
    }

    public void save() {

        //check for empty edittext
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences=getActivity().getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        final String ID=preferences.getString("id",null);

        String headUsername=username.getText().toString();
        final String fullName=fullname.getText().toString();
        final String headInstituteName=institutename.getText().toString();

//        String h_ID = HEAD_REF.push().getKey();
        Head h1=new Head(headUsername,fullName,headInstituteName);
        HEAD_REF.child(ID).setValue(h1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
            }
        });

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
                                    SharedPreferences pref=getActivity().getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
                                    SharedPreferences.Editor editor=pref.edit();
                                    editor.putString("username",newUsername);
                                    editor.commit();
                                    username.setText(newUsername);
                                    fullname.setText(fullName);
                                    institutename.setText(headInstituteName);
                                    Toast.makeText(getActivity(), "Successfully saved..!!", Toast.LENGTH_SHORT).show();
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

    public void changePassword() {
        Intent it=new Intent(getActivity(),ChangePassword.class);
        startActivity(it);

    }

}
