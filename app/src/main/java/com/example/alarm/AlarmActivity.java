package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlarmActivity extends AppCompatActivity {

    private Intent alarmServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        alarmServiceIntent = new Intent(getApplication(), AlarmService.class);
        stopService(alarmServiceIntent);
        Button noReminder = findViewById(R.id.no_reminder);
        Button reSetting = findViewById(R.id.resetting);
        Button reminder = findViewById(R.id.reminder);

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
                startService(alarmServiceIntent);
                finish();
            }
        });
    }
}
