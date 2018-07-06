package com.example.lrandom.notifydemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class MyService extends Service {
    NotificationManager notificationManager;
    MyBroadCast myBroadCast;
    MediaPlayer mediaPlayer;

    public static final String PLAY = "com.lrandom.PLAY";
    public static final String PAUSE = "com.lrandom.PAUSE";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this,R.raw.file);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1 = new Intent(getApplication(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this,0,intent1,0);

        Intent intentPlay = new Intent(PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(getBaseContext(),0,intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentPause = new Intent(PAUSE);
        PendingIntent pendingIntentPause = PendingIntent.getBroadcast(getBaseContext(),0,intentPause,PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteView = new RemoteViews(getPackageName(),R.layout.custom_notification_view);
        remoteView.setOnClickPendingIntent(R.id.btnPlay,pendingIntentPlay);
        remoteView.setOnClickPendingIntent(R.id.btnPause,pendingIntentPause);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Start Service")
                .setContentText("This is notify of service")
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteView);

        notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

        myBroadCast =new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PLAY);
        intentFilter.addAction(PAUSE);
        registerReceiver(myBroadCast,intentFilter);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        notificationManager.cancel(1);
        unregisterReceiver(myBroadCast);
        super.onDestroy();
    }

    class  MyBroadCast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(PLAY)){
                mediaPlayer.start();
            }

            if(intent.getAction().equals(PAUSE)){
                mediaPlayer.pause();
            }
        }
    }
}
