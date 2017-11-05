package com.example.kaushal.notifier;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class HomeStudentFragment extends Fragment {

    RelativeLayout profileBlock,scheduleBlock,sendReportBlock;
    View view;

    public HomeStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_student, container, false);

        profileBlock=(RelativeLayout) view.findViewById(R.id.profileBlock);
        scheduleBlock=(RelativeLayout) view.findViewById(R.id.scheduleBlock);
        sendReportBlock=(RelativeLayout) view.findViewById(R.id.sendReportBlock);


        profileBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new ChangPasswordFragment());
                fragmentTransaction.commit();
            }
        });

        scheduleBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new ViewStudentMyScheduleFragment());
                fragmentTransaction.commit();
            }
        });

        sendReportBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new CreateQueryReportFragment());
                fragmentTransaction.commit();
            }
        });




        return view;
    }

}
