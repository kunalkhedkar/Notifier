package com.example.kaushal.notifier;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by kaushal on 29/10/2017.
 */

public class UnSubscribeTopic {

    public static void unSubscribe(Context context){
        DatabaseReference USER_REFEENCE = FirebaseDatabase.getInstance().getReference("user");
        Toast.makeText(context, "unSubscribe from class", Toast.LENGTH_SHORT).show();
        FirebaseMessaging.getInstance().unsubscribeFromTopic("head");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("teacher");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("student");

        String classs[]=context.getResources().getStringArray(R.array.class_types);
        for(int i=0;i<classs.length;i++){
            FirebaseMessaging.getInstance().unsubscribeFromTopic(classs[i]);
        }


        USER_REFEENCE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Users uu = userDataSnapshot.getValue(Users.class);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(uu.getU_ID());
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
