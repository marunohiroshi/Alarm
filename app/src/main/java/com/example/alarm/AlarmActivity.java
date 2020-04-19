package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AlarmActivity extends AppCompatActivity {

    private Intent alarmServiceIntent;
    AlarmService mAlarmService;
    boolean mBound = false;

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ServiceConnection", "onServiceConnected");
            //Serviceとの接続確立時に呼び出される
            // service引数には、Onbind()で返却したBinderが渡される
            AlarmService.LocalBinder binder = (AlarmService.LocalBinder) service;
            AlarmActivity.this.mAlarmService = (AlarmService) binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected");
            // Serviceとの切断時に呼び出される。
            mAlarmService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        alarmServiceIntent = new Intent(getApplication(), AlarmService.class);
        stopService(alarmServiceIntent);
        Button noReminder = findViewById(R.id.no_reminder);
        Button reSetting = findViewById(R.id.resetting);
        Button reminder = findViewById(R.id.reminder);
        alarmServiceIntent = new Intent(getApplicationContext(), AlarmService.class);

        //再通知しない
        noReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //再設定
        reSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //再通知する
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                bindService(alarmServiceIntent,mConnection,Context.BIND_AUTO_CREATE);//バインドと同時にServiceの開始
                mBound = true;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }
}
