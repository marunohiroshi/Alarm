package com.example.alarm;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


class SharedPreferencesUtil {
    private static final String KEY_TIME = "TIME";

    private SharedPreferencesUtil() {//コンストラクタをprivateにしてインスタンス化させない
    }

    //SharedPreferences書き込み処理()
    static void setTime(Context context, long time) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_TIME, time);
        editor.apply();
    }

    //SharedPreferences読み込み処理()
    static long getTime(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(KEY_TIME, 0);
    }

}
