package com.example.kaushal.notifier;

import android.content.SharedPreferences;
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

public class ViewMyScheduleActivity extends AppCompatActivity {

    ListView myschedule;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    DatabaseReference SCHEDULE_REFERENCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_schedule);

        myschedule= (ListView) findViewById(R.id.my_schedule);
        list=new ArrayList<String>();

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        myschedule.setAdapter(adapter);

        SharedPreferences preference=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        final String username=preference.getString("username",null);
        Log.d("TAG", "onCreate: "+username);


        SCHEDULE_REFERENCE= FirebaseDatabase.getInstance().getReference("mainschedule");

        SCHEDULE_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDataSnapShot:dataSnapshot.getChildren()){
                    Schedule schedule=userDataSnapShot.getValue(Schedule.class);
                    String dbusername=schedule.getT_username();
                    Log.d("TAG", "username:"+dbusername);

                    if(username.equals(dbusername)){
                        list.add("Teacher name:\t"+schedule.getT_name()+"\n"+"Subject:\t"+schedule.getSub_name()+"\n"+"Marks:\t"+schedule.getMarks()+"\n"+"Description:\t"+schedule.getDescription()+"\n"+"date:\t"+schedule.getS_date()+"\n"+"Time:\t"+schedule.getS_time()+"\n"+"Class:"+schedule.getClassType()+"\n"+"Test Type:\t"+schedule.getSubjectType());
                        adapter.notifyDataSetChanged();
                        break;
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
