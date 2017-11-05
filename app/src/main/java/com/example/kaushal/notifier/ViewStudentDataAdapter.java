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

import static com.example.kaushal.notifier.R.id.scheduleList;

/**
 * Created by kaushal on 05/11/2017.
 */


public class ViewStudentDataAdapter extends RecyclerView.Adapter<ViewStudentDataAdapter.ViewHolder> {

    private ArrayList<Student> studentList;
    View view;

    public ViewStudentDataAdapter(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public ViewStudentDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_single_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewStudentDataAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.name.setText(studentList.get(i).getS_Name());
        viewHolder.username.setText(studentList.get(i).gets_Username());
        viewHolder.classType.setText(studentList.get(i).getS_Class());

    }


    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, username, classType;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.studentname);
            username = (TextView) view.findViewById(R.id.studentusername);
            classType = (TextView) view.findViewById(R.id.studentclasstype);

        }


    }
}