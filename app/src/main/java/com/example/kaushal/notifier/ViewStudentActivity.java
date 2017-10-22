package com.example.kaushal.notifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewStudentActivity extends AppCompatActivity {


    DatabaseReference STUDENT_REFERENCE;
    ListView studentList;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        STUDENT_REFERENCE= FirebaseDatabase.getInstance().getReference("student");
        studentList= (ListView) findViewById(R.id.studentList);
        list=new ArrayList<String>();


        buildStudentData();

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        studentList.setAdapter(adapter);


    }

    public void buildStudentData(){
        STUDENT_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                    Student ss=userDataSnapshot.getValue(Student.class);
                    list.add(ss.getS_Name()+"\n"+ss.gets_Username()+"\n"+ss.getS_Class());
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }










}
