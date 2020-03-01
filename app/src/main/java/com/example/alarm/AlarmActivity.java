package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

public class AlarmActivity extends AppCompatActivity {

    Button noReminder;
    Button reSetting;
    Button reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        noReminder = findViewById(R.id.no_reminder);
        reSetting = findViewById(R.id.resetting);
        reminder = findViewById(R.id.reminder);

        noReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //現在MainActivityで設定している時間の情報を取得して再度設定
                setReSetting();
            }
        });
    }

    private void setReSetting() {
        CountDownTimer cdt;
        cdt = new CountDownTimer(SharedPreferencesUtil.getTime(this, SharedPreferencesUtil.KEY_TIME), 1000) {//(カウントする時間, カウントする間隔), time秒間を1秒間隔でカウントする
            @Override
            public void onTick(long millisUntilFinished) {
                //ある一定時間毎に実行する処理
            }

            @Override
            public void onFinish() {
                //終わった時に実行する実行する処理
                Intent intent = new Intent(getApplication(), AlarmActivity.class);
                startActivity(intent);
            }
        };
        cdt.start();
        finish();
    }
}
