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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewQueryReportFragment extends Fragment {
    View view;
    DatabaseReference QUERY_REF;
    RecyclerView.Adapter adapter;
    ProgressBar progressBar;
    private ArrayList<QueryRepotClass> queryObjectList;


    public ViewQueryReportFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_view_query_report, container, false);
        QUERY_REF = FirebaseDatabase.getInstance().getReference("query2");
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Query Report");

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
        queryObjectList = new ArrayList<>();

        initScheduleList();

        Log.d("TAG", "initViews: list size " + queryObjectList.size());
        adapter = new ViewQuertReportAdapter(queryObjectList);
        recyclerView.setAdapter(adapter);

    }

    private void initScheduleList() {
        SharedPreferences preferences=getActivity().getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        final String tusername=preferences.getString("username",null);

        QUERY_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                queryObjectList.clear();
                for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                    QueryRepotClass query=userDataSnapshot.getValue(QueryRepotClass.class);
                    if(tusername.equals(query.getTeacherUsername())){
                        queryObjectList.add(query);
                        //list.add("Student Name:"+query.getStudentName()+"\n"+"Student Class:"+query.getStudentClass()+"\n"+"Absenty Report:"+query.getQueryDescription());
                        adapter.notifyDataSetChanged();

                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        progressBar.setVisibility(View.GONE);
    }

}
