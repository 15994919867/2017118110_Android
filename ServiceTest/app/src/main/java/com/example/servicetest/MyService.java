package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload(){
            Log.d("MyService", "执行 startDownload");
        }

        public int getProgress(){
            Log.d("MyService", "执行 getProgress");
            return 0;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("MyService","执行 onCreate");
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //只在Android O之上需要渠道，这里的第一个参数要和下面的channelId一样
        NotificationChannel notificationChannel = new NotificationChannel("1","name",NotificationManager.IMPORTANCE_HIGH);
        //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
        nm.createNotificationChannel(notificationChannel);

        Notification notification = new NotificationCompat.Builder(this,"1")
                .setContentTitle("This is content title")
                .setContentText("This is content text！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();
        nm.notify(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("MyService","执行 onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyService","执行 onDestroy");
    }
}
