package com.example.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
            AlarmDialogFragment alarmDialogFragment = new AlarmDialogFragment();
            alarmDialogFragment.show(getSupportFragmentManager(), "dialog");
        }
        return true;
    }

    public class AlarmDialogFragment extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            RadioGroup radioGroup = findViewById(R.id.radio_group);
            RadioButton tenSecond = findViewById(R.id.radioButton_ten_second);
            RadioButton thirtySecond = findViewById(R.id.radioButton_thirty_second);
            RadioButton sixtySecond = findViewById(R.id.radioButton_sixty_second);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (checkedId == tenSecond.getId()) {
                        time = 10;
                    }
                    if (checkedId == thirtySecond.getId()) {
                        time = 30;
                    }
                    if (checkedId == sixtySecond.getId()) {
                        time = 60;
                    }
                }
            });
            return inflater.inflate(R.layout.alarm_dialog, null, false);
        }
    }
}

E/AndroidRuntime: FATAL EXCEPTION: main
        Process: com.example.alarm, PID: 10178
        java.lang.IllegalStateException: Fragment com.example.alarm.MainActivity.AlarmDialogFragment must be a public static class to be  properly recreated from instance state.
        at androidx.fragment.app.BackStackRecord.doAddOp(BackStackRecord.java:400)
        at androidx.fragment.app.BackStackRecord.add(BackStackRecord.java:379)
        at androidx.fragment.app.DialogFragment.show(DialogFragment.java:143)
        at com.example.alarm.MainActivity.onOptionsItemSelected(MainActivity.java:37)
        at android.app.Activity.onMenuItemSelected(Activity.java:3543)
        at androidx.fragment.app.FragmentActivity.onMenuItemSelected(FragmentActivity.java:436)
        at androidx.appcompat.app.AppCompatActivity.onMenuItemSelected(AppCompatActivity.java:196)
        at androidx.appcompat.view.WindowCallbackWrapper.onMenuItemSelected(WindowCallbackWrapper.java:109)
        at androidx.appcompat.app.AppCompatDelegateImpl.onMenuItemSelected(AppCompatDelegateImpl.java:888)
        at androidx.appcompat.view.menu.MenuBuilder.dispatchMenuItemSelected(MenuBuilder.java:840)
        at androidx.appcompat.view.menu.MenuItemImpl.invoke(MenuItemImpl.java:158)
        at androidx.appcompat.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:991)
        at androidx.appcompat.view.menu.MenuPopup.onItemClick(MenuPopup.java:128)
        at android.widget.AdapterView.performItemClick(AdapterView.java:318)
        at android.widget.AbsListView.performItemClick(AbsListView.java:1159)
        at android.widget.AbsListView$PerformClick.run(AbsListView.java:3136)
        at android.widget.AbsListView$3.run(AbsListView.java:4052)
        at android.os.Handler.handleCallback(Handler.java:873)
        at android.os.Handler.dispatchMessage(Handler.java:99)
        at android.os.Looper.loop(Looper.java:193)
        at android.app.ActivityThread.main(ActivityThread.java:6669)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:493)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:858)
