package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
                //現在MainActivityで設定している時間の情報を取得して再度設定
                Intent intent = new Intent(getApplication(), AlarmService.class);
                startService(intent);
                finish();
            }
        });
    }
}
