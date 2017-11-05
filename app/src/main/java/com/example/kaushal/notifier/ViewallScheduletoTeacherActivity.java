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

public class ViewallScheduletoTeacherActivity extends AppCompatActivity {
    DatabaseReference TEACHER_REF;
    ListView schedulelist;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall_scheduleto_teacher);

        TEACHER_REF= FirebaseDatabase.getInstance().getReference("mainschedule");
        schedulelist= (ListView) findViewById(R.id.scheduleList);
        list=new ArrayList<String>();

        buildScheduleData();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        schedulelist.setAdapter(adapter);
    }
    public void buildScheduleData(){
        TEACHER_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                    Schedule schedule=userDataSnapshot.getValue(Schedule.class);
                    list.add("Teacher name:\t"+schedule.getT_name()+"\n"+"Subject:\t"+schedule.getSub_name()+"\n"+"Marks:\t"+schedule.getMarks()+"\n"+"Description:\t"+schedule.getDescription()+"\n"+"date:\t"+schedule.getS_date()+"\n"+"Time:\t"+schedule.getS_time()+"\n"+"Class:"+schedule.getClassType()+"\n"+"Test Type:\t"+schedule.getSubjectType());
                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
