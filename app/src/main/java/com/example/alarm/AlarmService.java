package com.example.alarm;


import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public class AlarmService extends Service {
    private CountDownTimer countDownTimer;

    public AlarmService() {
    }

    //初期化
    @Override
    public void onCreate() {
    }

    //サービスで実行させたい処理を記載
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        countDownTimer = new CountDownTimer(SharedPreferencesUtil.getTime(getApplication()), 1000) {
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
        return START_STICKY;
    }


    //破棄される際に呼ばれる
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        countDownTimer.cancel();
    }

    //bindService()で呼び出した場合、onStartCommand()ではなくonBind()が呼ばれる
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
