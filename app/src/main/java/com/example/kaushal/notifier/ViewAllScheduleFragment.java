package com.example.kaushal.notifier;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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


public class ViewAllScheduleFragment extends Fragment {

    View view;
    DatabaseReference SCHEDULE_REFERENCE;
    RecyclerView.Adapter adapter;
    ProgressBar progressBar;


    public ViewAllScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_all_schedule, container, false);
        SCHEDULE_REFERENCE = FirebaseDatabase.getInstance().getReference("mainschedule");
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All Schedule");

        progressBar = (ProgressBar) view.findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        initViews();


        return view;
    }


    private ArrayList<Schedule> scheduleObjectList;

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

        SCHEDULE_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                scheduleObjectList.clear();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Schedule schedule = userDataSnapshot.getValue(Schedule.class);
                    scheduleObjectList.add(schedule);
                    adapter.notifyDataSetChanged();

                }
                Collections.sort(scheduleObjectList, new CustomComparator());
                progressBar.setVisibility(View.GONE);
                for(Schedule schedule:scheduleObjectList){
                    Log.d("TAG", "date : "+schedule.getS_date());
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

                return date22.compareTo(date11);

            } catch (Exception e) {
                Toast.makeText(getActivity(), "exp", Toast.LENGTH_SHORT).show();
            }


            return 0;
        }


    }

}