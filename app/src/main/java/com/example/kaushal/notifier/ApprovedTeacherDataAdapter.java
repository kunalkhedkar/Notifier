package com.example.kaushal.notifier;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by kunal on 4/11/17.
 */


public class ApprovedTeacherDataAdapter extends RecyclerView.Adapter<ApprovedTeacherDataAdapter.ViewHolder> {

    private ArrayList<Teacher> teacherList;
    View view;
    Context context;

    public ApprovedTeacherDataAdapter(ArrayList<Teacher> teacherList, Context context) {
        this.teacherList = teacherList;
        this.context=context;
    }

    @Override
    public ApprovedTeacherDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_teacher_single_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ApprovedTeacherDataAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.name.setText(teacherList.get(i).getT_Name());
        viewHolder.username.setText(teacherList.get(i).getT_Username());

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if(!teacherList.isEmpty()){
                                    approvedTeacher(i);
                                }
                                break;


                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }

                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you Want To Accept this Teacher ").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, username;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.teachername);
            username = (TextView) view.findViewById(R.id.teacherusername);
            imageView=(ImageView) view.findViewById(R.id.approvedID);
        }

    }


    private void approvedTeacher(int i) {
        Teacher teacher = teacherList.get(i);
        //
        final String delTid=teacher.getT_ID();

        DatabaseReference deleteTeacherRef = FirebaseDatabase.getInstance().getReference("teacher").child(delTid);
        deleteTeacherRef.removeValue();
        notifyItemRemoved(i);

        DatabaseReference saveTeacherRef = FirebaseDatabase.getInstance().getReference("mainteacher");


        final String t_ID = saveTeacherRef.push().getKey();
        teacher.setT_ID(t_ID);
        saveTeacherRef.child(t_ID).setValue(teacher);
//        SharedPreferences s1 = getSharedPreferences(MyConstant.SHARED_FILE, MODE_PRIVATE);
//        SharedPreferences.Editor editor = s1.edit();
//        editor.putString("id", t_ID);

        //add to user table
        DatabaseReference USER_REFERENCE = FirebaseDatabase.getInstance().getReference("user");
        final String ID = USER_REFERENCE.push().getKey();
        Users uu = new Users(t_ID, teacher.getT_Username(), teacher.getT_Password(), teacher.getT_Role(), "token");
        USER_REFERENCE.child(ID).setValue(uu).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                CreateNotification createNotification=new CreateNotification(context);
                createNotification.sendNotificationTopic("Approved Account","Your account has been sucessfully approved",delTid);
                Toast.makeText(context, "sent", Toast.LENGTH_SHORT).show();
            }
        });

    }
}