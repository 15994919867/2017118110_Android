package com.example.servicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.Provider;

public class MainActivity extends AppCompatActivity implements View .OnClickListener{

    private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        Button bindService = (Button)findViewById(R.id.bind_service);
        Button unbindService = (Button)findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        Button startIntentService = (Button)findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.start_service:
                Intent startIntent = new Intent(this,MyService.class);
                //启动服务
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this,MyService.class);
                //停止服务
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                //绑定服务
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                //取消绑定服务
                unbindService(connection);
                break;
            case R.id.start_intent_service:
                //打印主线程的 id
                Log.d("MainActivity", "主进程的 id :"+Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                //启动意图服务
                startService(intentService);
                break;
            default:
                break;
        }
    }
}
