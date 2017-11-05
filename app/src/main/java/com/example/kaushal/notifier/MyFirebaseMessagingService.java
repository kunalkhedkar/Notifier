package com.example.kaushal.notifier;

/**
 * Created by kaushal on 29/10/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.R.id.message;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MSG";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
////                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
////                handleNow();
//            }
//
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.

    }

    @Override
    public void handleIntent(Intent intent) {
//        super.handleIntent(intent);

        //notification comes here

        Log.d(TAG, "handleIntent: "+intent.getExtras());
        String title=intent.getStringExtra("title");
        String msg=intent.getStringExtra("text");

//        MainActivity.info=intent.getExtras()+"";
        Log.d(TAG, "handleIntent: "+title+"\n"+msg);



        NotificationCompat.Builder b = new NotificationCompat.Builder(this.getApplicationContext());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, SplashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.my_logo)
                .setTicker("{your tiny message}")
                .setContentTitle(title)
                .setContentText(msg)
                .setContentInfo("INFO")
                .setContentIntent(contentIntent);


        NotificationManager nm = (NotificationManager) this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, b.build());


    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG, "onDeletedMessages: ");
    }
}


//https://stackoverflow.com/questions/37358462/firebase-onmessagereceived-not-called-when-app-in-background