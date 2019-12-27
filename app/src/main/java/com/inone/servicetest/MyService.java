package com.inone.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    private static final String TAG = "MyService";

    private DownloadBinder mBinder = new DownloadBinder();
    class DownloadBinder extends Binder {


        public void startDownload() {
            Log.d(TAG, "startDownload: *************");
        }

        public int getProgress() {
            Log.d(TAG, "getProgress: ****");
            return 0;
        }
    }
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

//创建服务时调用第一次创建的时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: **************************");
        String id = "com.inone.servicetes";
        //创建通道
        NotificationChannel channel = new NotificationChannel(id,"xxx", NotificationManager.IMPORTANCE_LOW);
        //状态栏通知管理类
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi  = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this).setChannelId(id)
                .setContentTitle("这是一个标题")
                .setContentText("这是文本内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
        
    }

    //每次服务启动时候调用（若希望服务已启动就执行的动作，卸载这个方法里）
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: **********************");
        return super.onStartCommand(intent, flags, startId);
    }


//服务销毁的时候调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: **********************");
    }
}
