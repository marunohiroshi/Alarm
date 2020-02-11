package com.example.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_notification_time_setting) {
            showMyDialog();
        }
        return true;
    }

    //ダイアログを作る。
    public void showMyDialog() {
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialog_view = inflater.inflate(R.layout.alarm_dialog, null);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedRadioButtonId) {
                final RadioButton tenSecond = findViewById(R.id.radioButton_ten_second);
                final RadioButton thirtySecond = findViewById(R.id.radioButton_thirty_second);
                final RadioButton sixtySecond = findViewById(R.id.radioButton_sixty_second);
                if (checkedRadioButtonId == tenSecond.getId()) {
                    time = 10;
                }
                if (checkedRadioButtonId == thirtySecond.getId()) {
                    time = 30;
                }
                if (checkedRadioButtonId == sixtySecond.getId()) {
                    time = 60;
                }
            }
        });
        builder.setView(dialog_view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

}

//エラー文
///E/AndroidRuntime: FATAL EXCEPTION: main
//        Process: com.example.alarm, PID: 7894
//        java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.RadioGroup.setOnCheckedChangeListener(android.widget.RadioGroup$OnCheckedChangeListener)' on a null object reference
//        at com.example.alarm.MainActivity.showMyDialog(MainActivity.java:44)
//        at com.example.alarm.MainActivity.onOptionsItemSelected(MainActivity.java:33)
//        at android.app.Activity.onMenuItemSelected(Activity.java:3543)
//        at androidx.fragment.app.FragmentActivity.onMenuItemSelected(FragmentActivity.java:436)
//        at androidx.appcompat.app.AppCompatActivity.onMenuItemSelected(AppCompatActivity.java:196)
//        at androidx.appcompat.view.WindowCallbackWrapper.onMenuItemSelected(WindowCallbackWrapper.java:109)
//        at androidx.appcompat.app.AppCompatDelegateImpl.onMenuItemSelected(AppCompatDelegateImpl.java:888)
//        at androidx.appcompat.view.menu.MenuBuilder.dispatchMenuItemSelected(MenuBuilder.java:840)
//        at androidx.appcompat.view.menu.MenuItemImpl.invoke(MenuItemImpl.java:158)
//        at androidx.appcompat.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:991)
//        at androidx.appcompat.view.menu.MenuPopup.onItemClick(MenuPopup.java:128)
//        at android.widget.AdapterView.performItemClick(AdapterView.java:318)
//        at android.widget.AbsListView.performItemClick(AbsListView.java:1159)
//        at android.widget.AbsListView$PerformClick.run(AbsListView.java:3136)
//        at android.widget.AbsListView$3.run(AbsListView.java:4052)
//        at android.os.Handler.handleCallback(Handler.java:873)
//        at android.os.Handler.dispatchMessage(Handler.java:99)
//        at android.os.Looper.loop(Looper.java:193)
//        at android.app.ActivityThread.main(ActivityThread.java:6669)
//        at java.lang.reflect.Method.invoke(Native Method)
//        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:493)
//        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:858)
