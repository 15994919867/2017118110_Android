package com.example.mydemo5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static TextView text;
    public static int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button:
                MyThread myThread = new MyThread();
                new Thread(myThread).start();
                break;
            default:
                break;
        }
    }
}

class MyThread implements Runnable {
    public static final int UPDATE_TEXT = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    MainActivity.text.setText("我是："+MainActivity.i);
                    MainActivity.i++;
                    break;
                default:break;
            }
        }
    };

    @Override
    public void run(){
        while(true) {
            Message message = new Message();
            message.what = UPDATE_TEXT;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendMessage(message);
        }
    }
}
