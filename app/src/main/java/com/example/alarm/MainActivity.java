package com.example.alarm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    CountDownTimer cdt;
    TextView textViewCountDown;
    TextView textViewState;
    Button ButtonAlarmStart;
    Button ButtonAlarmEnd;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewCountDown = findViewById(R.id.notification_time);
        textViewState = findViewById(R.id.state);
        ButtonAlarmStart = findViewById(R.id.notification_start);
        ButtonAlarmEnd = findViewById(R.id.notification_end);


        //通知開始ボタン
        ButtonAlarmStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.start();
                textViewState.setText("通知実行中");
            }
        });

        //通知終了ボタン
        ButtonAlarmEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewState.getText().equals("通知実行中")) {
                    textViewState.setText("通知停止中");
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
        if (textViewState.getText().equals("通知実行中")) {
            Context context = getApplicationContext();
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
            cdt = new CountDownTimer(SharedPreferencesUtil.getTime(context, SharedPreferencesUtil.KEY_TIME), 1000) {//(カウントする時間, カウントする間隔), time秒間を1秒間隔でカウントする
                @Override
                public void onTick(long millisUntilFinished) {
                    //ある一定時間毎に実行する処理
                }

                @Override
                public void onFinish() {
                    //終わった時に実行する実行する処理
                    Intent intent = new Intent(getApplication(), AlarmActivity.class);
                    startActivity(intent);
                    textViewState.setText("通知停止中");
                }
            };
            textViewCountDown.setText(String.format("%s秒", String.valueOf(SharedPreferencesUtil.getTime(context, SharedPreferencesUtil.KEY_TIME) / 1000)));
        }
    }
}
