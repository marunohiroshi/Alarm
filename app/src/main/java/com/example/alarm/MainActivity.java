package com.example.alarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static CountDownTimer cdt;
    //    long time = 0;
    TextView TextViewCountDown;
    TextView TextViewState;
    Button ButtonAlarmStart;
    Button ButtonAlarmEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextViewCountDown = findViewById(R.id.notification_time);
        TextViewState = findViewById(R.id.state);
        ButtonAlarmStart = findViewById(R.id.notification_start);
        ButtonAlarmEnd = findViewById(R.id.notification_end);
        SharedPreferences sharedPreferences = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        long time = sharedPreferences.getLong("KEY_TIME",0);

        TextViewCountDown.setText(String.format("%s秒", String.valueOf(time / 1000)));

        cdt = new CountDownTimer(time, 1000) {//(カウントする時間, カウントする間隔), time秒間を1秒間隔でカウントする
            @Override
            public void onTick(long millisUntilFinished) {
                //ある一定時間毎に実行する処理
            }

            @Override
            public void onFinish() {
                //終わった時に実行する実行する処理
                Intent intent = new Intent(getApplication(), AlarmActivity.class);
                startActivity(intent);
                TextViewState.setText("通知停止中");
            }
        };

        ButtonAlarmStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.start();
                TextViewState.setText("通知実行中");
            }
        });

        ButtonAlarmEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextViewState.getText().equals("通知実行中")) {
                    TextViewState.setText("通知停止中");
                }
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
        if (TextViewState.getText().equals("通知実行中")) {
            Context context = getApplicationContext();
            CharSequence text = "通知実行中に設定は使用できません";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            AlarmDialogFragment alarmDialogFragment = new AlarmDialogFragment();
            alarmDialogFragment.show(getSupportFragmentManager(), "dialog");
        }
        return true;
    }
}
