package com.example.alarm;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


class SharedPreferencesUtil {
    static final String KEY_TIME = "TIME";

    private SharedPreferencesUtil() {//コンストラクタをprivateにしてインスタンス化させない
    }

    //SharedPreferences書き込み処理()
    static void setTime(Context context, String key, long time) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, time);
        editor.apply();
    }

    //SharedPreferences読み込み処理()
    static long getTime(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0);
    }

}
