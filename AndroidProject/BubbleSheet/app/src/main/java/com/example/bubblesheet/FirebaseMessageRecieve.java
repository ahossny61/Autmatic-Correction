package com.example.bubblesheet;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;


import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageRecieve extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       if(remoteMessage.getData().size()>0){
           showNotification(remoteMessage.getData().get("tittle"),remoteMessage.getData().get("message"));
       }
       if(remoteMessage.getNotification()!=null){
           showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
       }
    }
    private RemoteViews getCustomDesign(String tittle,String message){
        RemoteViews remoteViews=new RemoteViews(getApplicationContext().getPackageName(),R.layout.notification);
        remoteViews.setTextViewText(R.id.notification_tittle,tittle);
        remoteViews.setTextViewText(R.id.notification_message,message);
        return remoteViews;
    }
    public void showNotification(String title,String message){
        Intent intent=new Intent(this,MainActivity.class);
        String channel_id="web_app_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),channel_id)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN){
            builder=builder.setContent(getCustomDesign(title,message));

        }
        else{
            builder=builder.setContentTitle(title)

                    .setContentText(message);
        }
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(channel_id,"web_app",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri,null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0,builder.build());

    }
}
