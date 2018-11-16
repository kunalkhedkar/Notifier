package com.example.kaushal.notifier;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddScheduleFragment extends Fragment {
    DatabaseReference SCHEDULE_REFERENCE;
    EditText teacherName,subjectName,marks,date,time,testDescription;
    Spinner selectClass,selectType;
    String setEditText;
    String ID;
    View view;
    Button post;
    Context context;

    public AddScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_schedule, container, false);
        teacherName= (EditText) view.findViewById(R.id.teacherName);
        subjectName= (EditText) view.findViewById(R.id.subjectName);
        marks= (EditText) view.findViewById(R.id.subjectMarks);
        testDescription= (EditText) view.findViewById(R.id.description);
        date= (EditText) view.findViewById(R.id.date);
        time= (EditText) view.findViewById(R.id.s_time);
        selectClass= (Spinner) view.findViewById(R.id.selectClass);
        selectType= (Spinner) view.findViewById(R.id.selectTestType);
        post=(Button)view.findViewById(R.id.addSchedule);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Schedule");
        SCHEDULE_REFERENCE= FirebaseDatabase.getInstance().getReference("schedule");
        SharedPreferences preferences=getActivity().getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        String id =preferences.getString("id",null);
        final String user=preferences.getString("username",null);
        // String use =preferences.getString("username",null);

        context=getActivity();
        Log.d("TAG123", "onDataChange:"+id);
//                                      FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference teacherRef= FirebaseDatabase.getInstance().getReference("mainteacher");

        teacherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDataSnapShot:dataSnapshot.getChildren()){
                    Teacher tt=userDataSnapShot.getValue(Teacher.class);
                    if(tt.getT_Username().equals(user)) {
                        setEditText = tt.getT_Name();
                        break;
                    }
                }
                Log.d("TAG", "onDataChange:"+setEditText);

                teacherName.setText(setEditText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});


        // class and type of test
        String classs[]=getResources().getStringArray(R.array.class_types);
        String test_type[]=getResources().getStringArray(R.array.test_type);

        ArrayAdapter<String> adpater_class=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,classs);
        selectClass.setAdapter(adpater_class);
        ArrayAdapter<String> adapter_type=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,test_type);
        selectType.setAdapter(adapter_type);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                monthOfYear=monthOfYear+1;
                date.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }

        };
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=  new DatePickerDialog(getActivity(), datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                }
                datePickerDialog.show();

                // new DatePickerDialog(MainActivity.this,datePicker,1994,02,24).show();
            }
        });



        final TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener(){


            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AM_PM ;
                if(hourOfDay < 12) {
                    AM_PM = "AM";
                } else {
                    AM_PM = "PM";
                    hourOfDay-=12;
                }
                time.setText(hourOfDay+" : "+minute+" "+AM_PM);
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(getActivity(), timePicker, myCalendar
                        .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),false).show();

                // new DatePickerDialog(MainActivity.this,datePicker,1994,02,24).show();

            }
        });

            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    post.setClickable(true);
                    post();
                    post.setClickable(false);
                }
            });


        return view;
    }


    public void post() {

        String t_name=teacherName.getText().toString();
        String s_name=subjectName.getText().toString();
        String s_marks=marks.getText().toString();
        String s_description=testDescription.getText().toString();
        String s_date=date.getText().toString();
        String s_time=time.getText().toString();

        String s_classType=selectClass.getSelectedItem().toString();
        String s_testType=selectType.getSelectedItem().toString();

        if(t_name.isEmpty())
        {
            teacherName.setError("please enter teacher name");
            return;
        }
        if(s_name.isEmpty())
        {
            subjectName.setError("please enter teacher name");
            return;
        }
        if(s_date.isEmpty())
        {
            date.setError("please enter teacher name");
            return;
        }
        if(s_time.isEmpty())
        {
            time.setError("please enter teacher name");
            return;
        }
        if(s_classType.equals("select class")){
            Toast.makeText(getActivity(), "select class", Toast.LENGTH_SHORT).show();
            return;
        }
        if(s_testType.equals("Type of test")){
            Toast.makeText(getActivity(), "select type of test", Toast.LENGTH_SHORT).show();
            return;
        }


//        SharedPreferences p=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
//        ID=p.getString("id",null);
//
////setting teachername
//            DatabaseReference teacherRef=FirebaseDatabase.getInstance().getReference("mainteacher").child(ID);
//
//        teacherRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot userDataSnapShot:dataSnapshot.getChildren()){
//                    Teacher tt=userDataSnapShot.getValue(Teacher.class);
//                     setEditText=tt.getT_Name();
//                }
//                //Toast.makeText(ScheduleActivity.this, ""+setEditText, Toast.LENGTH_SHORT).show();
//                Log.d("TAG", "StringName:"+setEditText);
//
//                teacherName.setText(setEditText);
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

// get username from shardpref
        SharedPreferences pref=getActivity().getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        String username=pref.getString("username",null);
        final String title="New Schedule Posted";
        final String msg=username+"post a new schedule";
        String ID=SCHEDULE_REFERENCE.push().getKey();
        Log.d("TAG","Post"+ID);
        Schedule s=new Schedule(username,ID,t_name,s_name,s_marks,s_description,s_date,s_time,s_classType,s_testType);
        SCHEDULE_REFERENCE.child(ID).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                CreateNotification createNotification=new CreateNotification(getActivity());
                createNotification.sendNotificationTopic(title,msg,"head");
                Toast.makeText(getActivity(), "schedule add sucessfully", Toast.LENGTH_SHORT).show();
                ((TeacherNaviActivity)getActivity()).showHome();
            }
        });

        //oncomplete listener
        //create notification fot head

//        Toast.makeText(this, "schedule add sucessfully", Toast.LENGTH_SHORT).show();

//        ((TeacherNaviActivity)getActivity()).showHome();


    }

}


