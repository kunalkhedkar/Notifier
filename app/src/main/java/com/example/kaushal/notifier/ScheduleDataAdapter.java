package com.example.kaushal.notifier;

/**
 * Created by kaushal on 02/11/2017.
 */


import android.app.Activity;
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

public class ScheduleDataAdapter extends RecyclerView.Adapter<ScheduleDataAdapter.ViewHolder> {
    private ArrayList<Schedule> scheduleList;
    View view;

    public ScheduleDataAdapter(ArrayList<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @Override
    public ScheduleDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_item, viewGroup, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ScheduleDataAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.TeacherName.setText(scheduleList.get(i).getT_name());
        viewHolder.date.setText(scheduleList.get(i).getS_date());
        viewHolder.time.setText(scheduleList.get(i).getS_time());
        viewHolder.subject.setText(scheduleList.get(i).getSub_name());
        viewHolder.marks.setText(scheduleList.get(i).getMarks()+" marks");
        viewHolder.classType.setText(scheduleList.get(i).getClassType());
        viewHolder.desc.setText(scheduleList.get(i).getDescription());
        viewHolder.testType.setText(scheduleList.get(i).getSubjectType());
        Log.d("kunal", "onComplete: "+i);
        viewHolder.approvedimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!scheduleList.isEmpty()) {
                    approvedSchedule(i);
                }else {
                    Toast.makeText(view.getContext(), "empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void approvedSchedule(final int i) {
        final Schedule schedule=scheduleList.get(i);
//        Toast.makeText(this, ""+schedule.getT_name(),Toast.LENGTH_SHORT).show();
        Log.d("sch", "approvedSchedule: "+schedule.getSchedule_ID());
        DatabaseReference deleteScheRefdule= FirebaseDatabase.getInstance().getReference("schedule").child(schedule.getSchedule_ID());
        deleteScheRefdule.removeValue();

        DatabaseReference saveScheduleRef=FirebaseDatabase.getInstance().getReference("mainschedule");
        final DatabaseReference teacherRef=FirebaseDatabase.getInstance().getReference("mainteacher");

        String s_ID=saveScheduleRef.push().getKey();
        schedule.setSchedule_ID(s_ID);
        final String teacherUsername=schedule.getT_username();
        final String smsg=schedule.getT_name()+" Posted new Schedule";
        final String tmsg=schedule.getT_name()+"posted a new schedule for "+schedule.getClassType()+"of subject "+schedule.getSub_name()+" is approved";

        saveScheduleRef.child(s_ID).setValue(schedule).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(view.getContext(), "Done", Toast.LENGTH_SHORT).show();
                CreateNotification createNotification=new CreateNotification(view.getContext());
                createNotification.sendNotificationTopic("New schedule has been post",smsg,schedule.getClassType());
                Log.d("sch", "onComplete: "+schedule.getClassType());
                Log.d("sch", "onComplete: "+teacherUsername);

                teacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                            Teacher teacher=userDataSnapshot.getValue(Teacher.class);
                            if(teacher.getT_Username().equals(teacherUsername)){
                                CreateNotification createNotification=new CreateNotification(view.getContext());
                                createNotification.sendNotificationTopic("Approved schedule",tmsg,"teacher");
//                                createNotification.sendNotificationTopic("Approved schedule",tmsg,teacher.getT_Username());
                                Toast.makeText(view.getContext(), "teacher send", Toast.LENGTH_SHORT).show();
                                notifyItemRemoved(i);
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView TeacherName,date,time,subject,marks,classType,desc,testType;
        private ImageView approvedimg;

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
            approvedimg=(ImageView)view.findViewById(R.id.approvedID);
        }
    }



}