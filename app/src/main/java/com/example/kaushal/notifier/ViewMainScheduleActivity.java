package com.example.kaushal.notifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMainScheduleActivity extends AppCompatActivity {
    DatabaseReference MAIN_SCHEDULE_REFERENCE;
    ListView studentList;
    ArrayList<String> listData;
    ArrayAdapter<String>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main_schedule);

        MAIN_SCHEDULE_REFERENCE= FirebaseDatabase.getInstance().getReference("mainschedule");
        studentList= (ListView) findViewById(R.id.mainSchedule);
        listData=new ArrayList<String>();

        buildScheduleData();

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listData);
        studentList.setAdapter(adapter);


    }

    public void buildScheduleData(){
        MAIN_SCHEDULE_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                listData.clear();
                for(DataSnapshot userDataSnapShot:dataSnapshot.getChildren()){
                    Schedule schedule=userDataSnapShot.getValue(Schedule.class);
                    listData.add("Teacher name:\t"+schedule.getT_name()+"\n"+"Subject:\t"+schedule.getSub_name()+"\n"+"Marks:\t"+schedule.getMarks()+"\n"+"Description:\t"+schedule.getDescription()+"\n"+"date:\t"+schedule.getS_date()+"\n"+"Time:\t"+schedule.getS_time()+"\n"+"Class:"+schedule.getClassType()+"\n"+"Test Type:\t"+schedule.getSubjectType());
                    Log.d("TAG", "onDataChange: "+schedule.getT_name()+"\n"+schedule.getSub_name()+"\n"+schedule.getMarks()+"\n"+schedule.getDescription()+"\n"+schedule.getS_date()+"\n"+schedule.getS_time()+"\n"+schedule.getClassType()+""+schedule.getSubjectType());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
