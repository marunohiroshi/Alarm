package com.example.alarm;


import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public class AlarmService extends Service {

    public AlarmService(){
    }

    //初期化
    @Override
    public void onCreate() {
    }

    //サービスで実行させたい処理を記載
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CountDownTimer countDownTimer = new CountDownTimer(SharedPreferencesUtil.getTime(getApplication(), SharedPreferencesUtil.KEY_TIME),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        };
        countDownTimer.start();
        this.stopSelf();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    //bindService()で呼び出した場合、onStartCommand()ではなくonBind()が呼ばれる
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
