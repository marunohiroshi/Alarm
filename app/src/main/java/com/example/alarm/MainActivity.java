package com.example.alarm;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telecom.ConnectionService;
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
    private long time = 60000;//初期設定時間60秒
    private Context context = this;
    private Intent intent = null;
    ServiceConnection mConnection;
    AlarmService mAlarmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countDownTextView = findViewById(R.id.notification_time);
        timerStateTextView = findViewById(R.id.state);
        Button alarmStartButton = findViewById(R.id.notification_start);
        Button alarmEndButton = findViewById(R.id.notification_end);
        setTextViewCountDown();//初期設定時間を記載
        intent = new Intent(this, AlarmService.class);

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //Serviceとの接続確立時に呼び出される
                setTimerStateTextView("通知実行中");
//                AlarmService.AlarmServiceLocalBinder binder = (AlarmService.AlarmServiceLocalBinder)service;
//                mAlarmService = binder.getService();

                AlarmService.AlarmServiceLocalBinder binder = (AlarmService.AlarmServiceLocalBinder) service;
                MainActivity.this.mConnection = (ServiceConnection) binder.getService();

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                setTimerStateTextView("通知停止中");
            }
        };

        //通知開始ボタン
        alarmStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimerStateTextView("通知実行中");

                bindService(intent, mConnection, BIND_AUTO_CREATE);//バインドと同時にServiceの開始
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

    public ServiceConnection getServiceConnection() {
        return mConnection;
    }

    //    @Override
//    protected void onRestart() {
//        super.onRestart();
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (AlarmService.class.getName().equals(serviceInfo.service.getClassName())) {
//                setTimerStateTextView("通知実行中");
//                // 実行中なら起動しない
//                return;
//            }
//            setTimerStateTextView("通知停止中");
//        }
//        setTextViewCountDown();
//    }
}
