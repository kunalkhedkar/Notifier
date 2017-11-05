package com.example.kaushal.notifier;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by kunal on 5/11/17.
 */


public class AllScheduleDataAdapter extends RecyclerView.Adapter<AllScheduleDataAdapter.ViewHolder> {
    private ArrayList<Schedule> scheduleList;
    View view;

    public AllScheduleDataAdapter(ArrayList<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @Override
    public AllScheduleDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plain_list_single_item, viewGroup, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(AllScheduleDataAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.TeacherName.setText(scheduleList.get(i).getT_name());
        viewHolder.date.setText(scheduleList.get(i).getS_date());
        viewHolder.time.setText(scheduleList.get(i).getS_time());
        viewHolder.subject.setText(scheduleList.get(i).getSub_name());
        viewHolder.marks.setText(scheduleList.get(i).getMarks()+" marks");
        viewHolder.classType.setText(scheduleList.get(i).getClassType());
        viewHolder.desc.setText(scheduleList.get(i).getDescription());
        viewHolder.testType.setText(scheduleList.get(i).getSubjectType());



    }



    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView TeacherName,date,time,subject,marks,classType,desc,testType;


        public ViewHolder(View view) {
            super(view);

            TeacherName = (TextView)view.findViewById(R.id.teacherId);
            date = (TextView)view.findViewById(R.id.dateID);
            time = (TextView)view.findViewById(R.id.timeID);
            subject = (TextView)view.findViewById(R.id.subjectID);
            marks = (TextView)view.findViewById(R.id.marksID);
            classType = (TextView)view.findViewById(R.id.classtypeID);
            desc = (TextView)view.findViewById(R.id.descriptionID);
            testType = (TextView)view.findViewById(R.id.testtypeID);
        }
    }



}
