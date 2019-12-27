package com.inone.servicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {//活动和服务成功绑定时候调用
            downloadBinder =(MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {//活动和服务断开的时候调用

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取startService，stopService按钮的实例
        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        Button bindService = (Button) findViewById(R.id.bind_service);
        Button unbindService = (Button) findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service:
                Intent intent = new Intent(this,MyService.class);
                startService(intent);//启动服务
                break;
            case R.id.stop_service:

                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);//停止服务
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);//第一个参数：Intent对象，第二个参数：ServiceConnection的实例。第三个参数：标志位，绑定服务 BIND_AUTO_CREATE自动创建服务
                break;
            case R.id.unbind_service:
             try {
                 unbindService(connection);//解绑定服务
             }catch (Exception e){
                 e.printStackTrace();
             }

                break;
                default:
                    break;
        }

    }
}
