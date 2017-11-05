package com.example.kaushal.notifier;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
 * A simple {@link Fragment} subclass.
 */
public class CreateQueryReportFragment extends Fragment {
    TextView studentName,absentyReport,studentClass;
    Spinner Tusername;
    DatabaseReference QUERY_REFERENCE;
    DatabaseReference   MAIN_TEACHER;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    View view;
    Button submit;


    public CreateQueryReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_create_query_report, container, false);

        Tusername= (Spinner) view.findViewById(R.id.TeacherUsername);
        studentName= (TextView) view.findViewById(R.id.studentName);
        absentyReport= (TextView) view.findViewById(R.id.absentyReport);
        studentClass= (TextView) view.findViewById(R.id.student_class);
        submit=(Button) view.findViewById(R.id.submitReport);
        QUERY_REFERENCE= FirebaseDatabase.getInstance().getReference("query2");
        MAIN_TEACHER= FirebaseDatabase.getInstance().getReference("mainteacher");
        list=new ArrayList<>();
        list.add("Select Teacher");

        fillTeacherList();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReport();
            }
        });

        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list  );
        Tusername.setAdapter(adapter);
        return view;


    }


    public void submitReport() {
        final String teacherUsername= (String) Tusername.getSelectedItem();
        final String sName=studentName.getText().toString();
        String sClass=studentClass.getText().toString();
        String report=absentyReport.getText().toString();
        if(teacherUsername.equalsIgnoreCase("select teacher")){
            Toast.makeText(getActivity(), "Please select Teacher name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(sName.isEmpty()){
            studentName.setError("please enter student name");
            return;

        }
        if(sClass.isEmpty()){
            studentClass.setError("please enter Class");
            return;
        }
        if(report.isEmpty()){
            absentyReport.setError("please write absent report");
            return;
        }




        String id = QUERY_REFERENCE.push().getKey();
        QueryRepotClass q=new QueryRepotClass(id,teacherUsername,sName,sClass,report);
        QUERY_REFERENCE.child(id).setValue(q).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Report Send Successfully", Toast.LENGTH_SHORT).show();
                String title="Absent report";
                String msg=sName+"sent Absent report";
                String to=teacherUsername;
                to=to.replace("@","_");
                to=to.replace(".","_");
                CreateNotification createNotification=new CreateNotification(getActivity());
                createNotification.sendNotificationTopic(title,msg,to);

            }
        });

    }
    public void fillTeacherList(){

        MAIN_TEACHER.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){
                    Teacher tt=userDataSnapshot.getValue(Teacher.class);
                    list.add(tt.getT_Username());

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
