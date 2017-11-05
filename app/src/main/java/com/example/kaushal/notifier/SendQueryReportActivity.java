package com.example.kaushal.notifier;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
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

public class SendQueryReportActivity extends AppCompatActivity {
    TextView studentName,absentyReport,studentClass;
    Spinner Tusername;
    DatabaseReference QUERY_REFERENCE;
    DatabaseReference   MAIN_TEACHER;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_query_report);
        Tusername= (Spinner) findViewById(R.id.TeacherUsername);
        studentName= (TextView) findViewById(R.id.studentName);
        absentyReport= (TextView) findViewById(R.id.absentyReport);
        studentClass= (TextView) findViewById(R.id.student_class);
        QUERY_REFERENCE= FirebaseDatabase.getInstance().getReference("query2");
        MAIN_TEACHER= FirebaseDatabase.getInstance().getReference("mainteacher");
        list=new ArrayList<>();
        list.add("Select Teacher");

        fillTeacherList();

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list  );
        Tusername.setAdapter(adapter);

    }


    public void submitReport(View view) {
        final String teacherUsername= (String) Tusername.getSelectedItem();
        final String sName=studentName.getText().toString();
        String sClass=studentClass.getText().toString();
        String report=absentyReport.getText().toString();
        if(teacherUsername.equalsIgnoreCase("select teacher")){
            Toast.makeText(this, "Please select Teacher name", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SendQueryReportActivity.this, "Report Send Successfully", Toast.LENGTH_SHORT).show();
                String title="Absent report";
                String msg=sName+"sent Absent report";
                String to=teacherUsername;
                to=to.replace("@","_");
                to=to.replace(".","_");
                CreateNotification createNotification=new CreateNotification(SendQueryReportActivity.this);
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
