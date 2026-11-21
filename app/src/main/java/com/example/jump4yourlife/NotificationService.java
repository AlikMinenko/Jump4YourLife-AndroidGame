package com.example.jump4yourlife;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.nio.channels.Channel;

import static com.example.jump4yourlife.MainActivity.CHANNEL_ID;

public class NotificationService extends Service {

    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle("Jump4YourLife").setContentText("Game is running").setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.lastpixelboy).build();
        startForeground(1,notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}