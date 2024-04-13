package com.dictionary.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dictionary.MainActivity;
import com.dictionary.R;

public class ReminderBroadcastReceiver extends BroadcastReceiver {
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_MUTABLE);

        createNotificationChanel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("You have an notify from Dictionary!!!!")
                .setContentText("Từ của ngày hôm nay là : Enjoy live!")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        managerCompat.notify(200, builder.build());
    }
    private void createNotificationChanel(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name =  "DictionaryChannel";
            String description = "Dictionary app remind you time to learn";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
