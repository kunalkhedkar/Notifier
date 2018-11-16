package com.example.kaushal.notifier;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeHeadFragment extends Fragment {
    RelativeLayout profileBlock,scheduleRequestBlock,scheduleReport,accountRequest;
    View view;


    public HomeHeadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_head, container, false);
        profileBlock=(RelativeLayout) view.findViewById(R.id.profileBlock);
        scheduleRequestBlock=(RelativeLayout) view.findViewById(R.id.scheduleRequestBlock);
        scheduleReport=(RelativeLayout) view.findViewById(R.id.scheduleReportBlock);
        accountRequest=(RelativeLayout) view.findViewById(R.id.accounrRequestBlock);

        profileBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new HeadProfileFragment());
                fragmentTransaction.commit();

            }
        });

        scheduleRequestBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new ScheduleRequestFragment());
                fragmentTransaction.commit();


            }
        });

        scheduleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new ViewAllScheduleFragment());
                fragmentTransaction.commit();


            }
        });
        accountRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new ApprovedTeacherFragment());
                fragmentTransaction.commit();


            }
        });
        return  view;


    }

}
