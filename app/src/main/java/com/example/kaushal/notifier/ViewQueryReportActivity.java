package com.example.kaushal.notifier;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewQueryReportActivity extends AppCompatActivity {
    DatabaseReference QUERY_REFERENCE;
    ListView queryList;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_query_report);
        QUERY_REFERENCE= FirebaseDatabase.getInstance().getReference("query2");
        queryList= (ListView) findViewById(R.id.queryReportlist);
        list=new ArrayList<String>();

        buildQueryReportdata();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        queryList.setAdapter(adapter);
    }


    public void buildQueryReportdata(){
        SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        final String tusername=preferences.getString("username",null);

        QUERY_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot userDataSnapshot:dataSnapshot.getChildren()){
                    QueryRepotClass query=userDataSnapshot.getValue(QueryRepotClass.class);
                    if(tusername.equals(query.getTeacherUsername())){
                        list.add("Student Name:"+query.getStudentName()+"\n"+"Student Class:"+query.getStudentClass()+"\n"+"Absenty Report:"+query.getQueryDescription());
                        adapter.notifyDataSetChanged();

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }




}
