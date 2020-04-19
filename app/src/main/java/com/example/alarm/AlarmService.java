package com.example.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;

import android.os.CountDownTimer;
import android.os.IBinder;

import static android.content.ContentValues.TAG;

public class AlarmService extends Service {
    private CountDownTimer countDownTimer;

    private final IBinder mBinder = new LocalBinder();

    //サービスに接続するためのBinder
    class LocalBinder extends Binder {
        //サービスの取得
        AlarmService getService() {
            return AlarmService.this;
        }
    }

    //開始時にコール
    @Override
    public IBinder onBind(Intent intent) {
        // onBind()で返すBinder、返した後はonServiceConnectedの第二引数に渡される
        Log.d(TAG, "onBind");

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
        int numb = intent.getIntExtra("numb", 0);
        Log.e("WillWolf", "numb-->" + numb);
        if (numb == 1) {
            Log.e("WillWolf", "local binder");
            return new LocalBinder();
        } else {
            Log.e("WillWolf", "remote binder");
            return mBinder;
        }
    }

    //Service切断時に呼ばれる
    //onBindをreturn trueでovrerideすると次回bind時にonRebindが呼ばれる
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return true;
    }

    //Unbind後に再接続される場合に呼ばれる
    @Override
    public void onRebind(Intent intent) {
        onBind(intent);
        super.onRebind(intent);
    }

    //Serviceのインスタンスがない状態で、クライアントがstartServiceまたはbindServiceを呼んだ時に
    //Serviceのインスタンス生成で呼ばれる。すでにインスタンスが存在している場合は呼ばれない。
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    //サービスで実行させたい処理を記載
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    //破棄される際に呼ばれる
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        stopSelf();
        countDownTimer.cancel();
    }
}
