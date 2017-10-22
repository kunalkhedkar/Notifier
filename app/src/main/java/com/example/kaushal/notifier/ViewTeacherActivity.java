package com.example.kaushal.notifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ViewTeacherActivity extends AppCompatActivity {

    ListView teacherList;
    ArrayList<String> listData;
    ArrayAdapter<String> adapter;

    DatabaseReference DUMMY_TEACHER_RERENCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher);

        teacherList= (ListView) findViewById(R.id.teacherList);
        DUMMY_TEACHER_RERENCE= FirebaseDatabase.getInstance().getReference("teacher");
        listData=new ArrayList();




        //for reading

        buildTeacherData();

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listData);
        teacherList.setAdapter(adapter);
    }

    public void buildTeacherData(){

        Log.d("TAG", "buildTeacherData: method call");
        DUMMY_TEACHER_RERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listData.clear();
                for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                    Teacher tt=userDataSnapshot.getValue(Teacher.class);
                    //String name=tt.getT_Name();
                    Log.d("TAG",tt.getT_Name());
                    //Toast.makeText(ViewTeacherActivity.this, name, Toast.LENGTH_SHORT).show();
                    listData.add(tt.getT_Name()+"\n"+tt.getT_Username());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
