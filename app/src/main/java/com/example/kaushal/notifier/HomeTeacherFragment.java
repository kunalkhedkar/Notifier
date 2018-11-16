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
public class HomeTeacherFragment extends Fragment {
    RelativeLayout profileBlock,MyScheduleBlock,AddScheduleBlock,ViewStudentBlock;
    View view;


    public HomeTeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_teacher, container, false);

        profileBlock=(RelativeLayout) view.findViewById(R.id.profileBlock);
        MyScheduleBlock=(RelativeLayout) view.findViewById(R.id.MyscheduleBlock);
        AddScheduleBlock=(RelativeLayout) view.findViewById(R.id.addScheduleBlock);
        ViewStudentBlock=(RelativeLayout) view.findViewById(R.id.ViewStudentBlock);

        profileBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new ChangPasswordFragment());
                fragmentTransaction.commit();

            }
        });

        MyScheduleBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new ViewTeacherMyScheduleFragment());
                fragmentTransaction.commit();


            }
        });

        AddScheduleBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new AddScheduleFragment());
                fragmentTransaction.commit();


            }
        });
        ViewStudentBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new ViewStudentFragment());
                fragmentTransaction.commit();


            }
        });
        return view;



    }

}
