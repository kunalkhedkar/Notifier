package com.example.kaushal.notifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Adapter;
import android.util.Log;
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
import  java.util.Collections;

public class ViewScheduleActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    DatabaseReference SCHEDULE_REFERENCE;
    ListView scheduleList;
    ArrayList<String> list;
    ArrayList<Schedule> ScheduleObjectList;
    ArrayAdapter<String> adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);


        SCHEDULE_REFERENCE= FirebaseDatabase.getInstance().getReference("schedule");
        scheduleList= (ListView) findViewById(R.id.scheduleList);
        list=new ArrayList<String>();
        ScheduleObjectList=new ArrayList<>();

        buildScheduledata();

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        scheduleList.setAdapter(adapter);

        scheduleList.setOnItemLongClickListener(this);


//        this.schedule_ID=schedule_ID;
//        this.t_name = t_name;
//        this.sub_name = sub_name;
//        this.marks = marks;
//        this.description=description;
//        this.s_date = s_date;
//        this.s_time = s_time;
//        this.classType = classType;
//        this.subjectType = subjectType;

    }
    public void buildScheduledata()
    {
        SCHEDULE_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                    Schedule schedule=userDataSnapshot.getValue(Schedule.class);
                    list.add("Teacher name:\t"+schedule.getT_name()+"\n"+"Subject:\t"+schedule.getSub_name()+"\n"+"Marks:\t"+schedule.getMarks()+"\n"+"Description:\t"+schedule.getDescription()+"\n"+"date:\t"+schedule.getS_date()+"\n"+"Time:\t"+schedule.getS_time()+"\n"+"Class:"+schedule.getClassType()+"\n"+"Test Type:\t"+schedule.getSubjectType());
                    ScheduleObjectList.add(schedule);
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

        final String delNum=list.get(i).toString();

        DialogInterface.OnClickListener dialogClickListener=new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface,int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                         approvedSchedule(i);
                        adapter.notifyDataSetChanged();
                        break;


                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;

                }

            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewScheduleActivity.this);
        builder.setMessage("Do you Want To Accept This Schedule ").setPositiveButton("Approve", dialogClickListener)
                .setNegativeButton("Reject", dialogClickListener).show();

        return true;

    }
    private void approvedSchedule(int i) {
        Schedule schedule=ScheduleObjectList.get(i);
        Toast.makeText(this, ""+schedule.getT_name(),Toast.LENGTH_SHORT).show();

        DatabaseReference deleteScheRefdule=FirebaseDatabase.getInstance().getReference("schedule").child(schedule.getSchedule_ID());
        deleteScheRefdule.removeValue();

        DatabaseReference saveScheduleRef=FirebaseDatabase.getInstance().getReference("mainschedule");


        String s_ID=saveScheduleRef.push().getKey();
        schedule.setSchedule_ID(s_ID);
        saveScheduleRef.child(s_ID).setValue(schedule);

    }

    }

