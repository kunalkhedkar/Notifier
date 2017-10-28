package com.example.kaushal.notifier;

import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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

        //studentList.setOnItemLongClickListener(this);
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

//
//    @Override
//    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//        //return false;
//
//        final String delNum=list.get(i).toString();
//
//        DialogInterface.OnClickListener dialogClickListener=new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                switch (which){
//                    case DialogInterface.BUTTON_POSITIVE:
//
//                        adapter.notifyDataSetChanged();
//                        break;
//
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        //No button clicked
//                        break;
//
//                }
//
//            }
//        };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudentActivity.this);
//        builder.setMessage("Are you want to accept Shcedule "+delNum+" ?").setPositiveButton("Yes", dialogClickListener)
//                .setNegativeButton("No", dialogClickListener).show();
//
//        return true;
//    }
}
