package com.example.kaushal.notifier;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewStudentMyScheduleFragment extends Fragment {
    View view;
    DatabaseReference SCHEDULE_REFERENCE;
    RecyclerView.Adapter adapter;
    ProgressBar progressBar;
    private ArrayList<Schedule> scheduleObjectList;
    private DatabaseReference STFUDENT_REF;


    public ViewStudentMyScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_student_my_schedule, container, false);
        SCHEDULE_REFERENCE = FirebaseDatabase.getInstance().getReference("mainschedule");
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Schedule");

        progressBar = (ProgressBar) view.findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        initViews();

        return view;
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        scheduleObjectList = new ArrayList<>();

        initScheduleList();

        Log.d("TAG", "initViews: list size " + scheduleObjectList.size());
        adapter = new AllScheduleDataAdapter(scheduleObjectList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
//                    Toast.makeText(getContext(), countries.get(position), Toast.LENGTH_SHORT).show();

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    private void initScheduleList() {

        SharedPreferences preference=getActivity().getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        final String username=preference.getString("username",null);
        Log.d("TAG", "onCreate: preference "+username);


        SCHEDULE_REFERENCE= FirebaseDatabase.getInstance().getReference("mainschedule");
        STFUDENT_REF= FirebaseDatabase.getInstance().getReference("student");

        STFUDENT_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                scheduleObjectList.clear();
                for(DataSnapshot userDataSnapshots:dataSnapshot.getChildren()){
                    final Student student=userDataSnapshots.getValue(Student.class);
                    if(username.equals(student.gets_Username())){
                        final String classType=student.getS_Class();
                        SCHEDULE_REFERENCE.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot userDataSnapshots1:dataSnapshot.getChildren()){
                                    Schedule schedule=userDataSnapshots1.getValue(Schedule.class);
                                    if(classType.equals(schedule.getClassType())){
                                       scheduleObjectList.add(schedule);
                                        adapter.notifyDataSetChanged();
                                        //listData.add("Teacher name:\t"+schedule.getT_name()+"\n"+"Subject:\t"+schedule.getSub_name()+"\n"+"Marks:\t"+schedule.getMarks()+"\n"+"Description:\t"+schedule.getDescription()+"\n"+"date:\t"+schedule.getS_date()+"\n"+"Time:\t"+schedule.getS_time()+"\n"+"Class:"+schedule.getClassType()+"\n"+"Test Type:\t"+schedule.getSubjectType());
                                    }
                                }
                                Collections.sort(scheduleObjectList,new CustomComparator());
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

            progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public class CustomComparator implements Comparator<Schedule> {

        @Override
        public int compare(Schedule schedule, Schedule t1) {

            String date1 = schedule.getS_date();
            String date2 = t1.getS_date();

            try {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/DD");

//                String str1 = "9/10/2015";
                Date date11 = formatter.parse(date1);

//                String str2 = "10/10/2015";
                Date date22 = formatter.parse(date2);

//                if (date1.compareTo(date2) < 0) {
//                    System.out.println("date2 is Greater than my date1");
//                }
                Log.d("TAG123", "compare:"+date11+"\t"+date22);
                return date22.compareTo(date11);

            } catch (Exception e) {
                Toast.makeText(getActivity(), "exp", Toast.LENGTH_SHORT).show();
            }


            return 0;
        }


    }
}