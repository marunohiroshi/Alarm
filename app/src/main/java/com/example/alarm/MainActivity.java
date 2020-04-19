package com.example.alarm;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView countDownTextView;
    private TextView timerStateTextView;
    private long time = 3000;//初期設定時間60秒
    private Context context = this;
    Intent intent = null;
    AlarmService mAlarmService;
    boolean mBound = false;
    ServiceConnection serviceConnection;

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ServiceConnection", "onServiceConnected");
            //Serviceとの接続確立時に呼び出される
            // service引数には、Onbind()で返却したBinderが渡される
            AlarmService.LocalBinder binder = (AlarmService.LocalBinder) service;
            MainActivity.this.mAlarmService = (AlarmService) binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected");
            // Serviceとの切断時に呼び出される。
            setTimerStateTextView("通知停止中");
            mAlarmService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countDownTextView = findViewById(R.id.notification_time);
        timerStateTextView = findViewById(R.id.state);
        Button alarmStartButton = findViewById(R.id.notification_start);
        Button alarmEndButton = findViewById(R.id.notification_end);
        setTextViewCountDown();//初期設定時間を記載
        intent = new Intent(context, AlarmService.class);
        serviceConnection = mConnection;

        //通知開始ボタン
        alarmStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimerStateTextView("通知実行中");
                bindService(intent,mConnection,Context.BIND_AUTO_CREATE);//バインドと同時にServiceの開始
                mBound = true;
            }
        });

        //通知終了ボタン
        alarmEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimerStateTextView("通知停止中");
                unbindService(mConnection);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (timerStateTextView.getText().equals("通知実行中")) {
            CharSequence text = "通知実行中に設定は使用できません";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            AlarmDialogFragment alarmDialogFragment = new AlarmDialogFragment();
            alarmDialogFragment.setOnDismissListener(new MyOnDismissListener());
            alarmDialogFragment.show(getSupportFragmentManager(), "dialog");
        }
        return true;
    }

    private class MyOnDismissListener implements DialogInterface.OnDismissListener {
        @Override
        public void onDismiss(DialogInterface dialog) {
            time = SharedPreferencesUtil.getTime(context);
            setTextViewCountDown();
        }
    }

    //通知時間表示
    void setTextViewCountDown() {
        countDownTextView.setText(String.format("%s秒", time / 1000));
    }

    //状態表示
    void setTimerStateTextView(String timerState) {
        this.timerStateTextView.setText(timerState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

}
