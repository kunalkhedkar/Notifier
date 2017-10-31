package com.example.kaushal.notifier;

import android.provider.Settings;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ViewMainScheduleActivity extends AppCompatActivity {
    DatabaseReference MAIN_SCHEDULE_REFERENCE;
    ListView studentList;
    ArrayList<String> listData;
    ArrayList<Schedule> listDataSchedule;
    ArrayAdapter<String>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main_schedule);

        MAIN_SCHEDULE_REFERENCE= FirebaseDatabase.getInstance().getReference("mainschedule");
        studentList= (ListView) findViewById(R.id.mainSchedule);
        listData=new ArrayList<String>();
        listDataSchedule=new ArrayList<>();

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
                    listDataSchedule.add(schedule);
                    listData.add("Teacher name:\t"+schedule.getT_name()+"\n"+"Subject:\t"+schedule.getSub_name()+"\n"+"Marks:\t"+schedule.getMarks()+"\n"+"Description:\t"+schedule.getDescription()+"\n"+"date:\t"+schedule.getS_date()+"\n"+"Time:\t"+schedule.getS_time()+"\n"+"Class:"+schedule.getClassType()+"\n"+"Test Type:\t"+schedule.getSubjectType());
                    Log.d("TAG", "onDataChange: "+schedule.getT_name()+"\n"+schedule.getSub_name()+"\n"+schedule.getMarks()+"\n"+schedule.getDescription()+"\n"+schedule.getS_date()+"\n"+schedule.getS_time()+"\n"+schedule.getClassType()+""+schedule.getSubjectType());
                    adapter.notifyDataSetChanged();
                }
//                Collections.reverse(listData);
                Collections.sort(listDataSchedule, new CustomComparator());
                listData.clear();
                for(Schedule schedule:listDataSchedule){
//                    Log.d("date", "onDataChange: "+schedule.getS_date());
                    listData.add("Teacher name:\t"+schedule.getT_name()+"\n"+"Subject:\t"+schedule.getSub_name()+"\n"+"Marks:\t"+schedule.getMarks()+"\n"+"Description:\t"+schedule.getDescription()+"\n"+"date:\t"+schedule.getS_date()+"\n"+"Time:\t"+schedule.getS_time()+"\n"+"Class:"+schedule.getClassType()+"\n"+"Test Type:\t"+schedule.getSubjectType());


                    Log.d("TAG", "onCreate: ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class CustomComparator implements Comparator<Schedule> {

        @Override
        public int compare(Schedule schedule, Schedule t1) {

            String date1=schedule.getS_date();
            String date2=t1.getS_date();

            try {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/DD");

//                String str1 = "9/10/2015";
                Date date11 = formatter.parse(date1);

//                String str2 = "10/10/2015";
                Date date22 = formatter.parse(date2);

//                if (date1.compareTo(date2) < 0) {
//                    System.out.println("date2 is Greater than my date1");
//                }

                return date22.compareTo(date11);

            }catch (Exception e){}




//            String s1[]=date1.split("/");
//            String s2[]=date2.split("/");
//
//            if(Integer.parseInt(s1[1])<10)
//                s1[1]="0"+s1[1];
//
//            if(Integer.parseInt(s1[2])<10)
//                s1[2]="0"+s1[2];
//
//            date1=s1[0]+s1[1]+s2[2];
//
//            //date2
//            if(Integer.parseInt(s2[1])<10)
//                s2[1]="0"+s2[1];
//
//            if(Integer.parseInt(s2[2])<10)
//                s2[2]="0"+s2[2];
//
//            date2=s2[0]+s2[1]+s2[2];



//            date1=date1.replace("/","");
//            date2=date2.replace("/","");

//            int d1=Integer.parseInt(date1);
//            int d2=Integer.parseInt(date2);

//            if(d1<d2)
//                return d1-d2;
//            else if(d1>d2)
//                return d1-d2;
//            else if(d1==d2)
//                return 0;

            return 0;
        }
    }


}
