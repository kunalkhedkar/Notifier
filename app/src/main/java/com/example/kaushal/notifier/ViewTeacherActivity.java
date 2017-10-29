package com.example.kaushal.notifier;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ViewTeacherActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    ListView teacherList;
    ArrayList<String> listData;
    ArrayList<Teacher> TeacherObjectList;
    ArrayAdapter<String> adapter;

    DatabaseReference DUMMY_TEACHER_RERENCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher);

        teacherList = (ListView) findViewById(R.id.teacherList);
        DUMMY_TEACHER_RERENCE = FirebaseDatabase.getInstance().getReference("teacher");
        listData = new ArrayList();
        TeacherObjectList = new ArrayList<>();

        //for reading

        buildTeacherData();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        teacherList.setAdapter(adapter);

        teacherList.setOnItemLongClickListener(this);
    }

    public void buildTeacherData() {

        Log.d("TAG", "buildTeacherData: method call");
        DUMMY_TEACHER_RERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listData.clear();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Teacher tt = userDataSnapshot.getValue(Teacher.class);
                    //String name=tt.getT_Name();
                    Log.d("TAG", tt.getT_Name());
                    //Toast.makeText(ViewTeacherActivity.this, name, Toast.LENGTH_SHORT).show();
                    listData.add(tt.getT_Name() + "\n" + tt.getT_Username());
                    TeacherObjectList.add(tt);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

        //return false;
        final String delnum = listData.get(i).toString();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        approvedTeacher(i);
                        adapter.notifyDataSetChanged();
                        break;


                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }

            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(ViewTeacherActivity.this);
        builder.setMessage("Do you Want To Accept this Teacher ").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
        return true;


    }

    private void approvedTeacher(int i) {
        Teacher teacher = TeacherObjectList.get(i);
        Toast.makeText(this, "" + teacher.getT_Username(), Toast.LENGTH_SHORT).show();

        DatabaseReference deleteTeacherRef = FirebaseDatabase.getInstance().getReference("teacher").child(teacher.getT_ID());
        deleteTeacherRef.removeValue();

        DatabaseReference saveTeacherRef = FirebaseDatabase.getInstance().getReference("mainteacher");


        String t_ID = saveTeacherRef.push().getKey();
        teacher.setT_ID(t_ID);
        saveTeacherRef.child(t_ID).setValue(teacher);
        SharedPreferences s1 = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = s1.edit();
        editor.putString("id", t_ID);

        //add to user table
        DatabaseReference USER_REFERENCE=FirebaseDatabase.getInstance().getReference("user");
        String ID=USER_REFERENCE.push().getKey();
        Users uu=new Users(t_ID,teacher.getT_Username(),teacher.getT_Password(),teacher.getT_Role(),"token");
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

    }
}