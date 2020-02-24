package com.example.alarm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AlarmDialogFragment extends DialogFragment {
    private static final String KEY_TIME = "TIME";
    private int time = 0;

//    static AlarmDialogFragment newInstance(int time) {
//        Bundle args = new Bundle();
//        AlarmDialogFragment fragment = new AlarmDialogFragment();
//        args.putInt(KEY_TIME,time);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.alarm_dialog, null);

        builder.setView(dialogView);
        RadioGroup radioGroup = dialogView.findViewById(R.id.radio_group);
        final RadioButton tenSecond = dialogView.findViewById(R.id.radioButton_ten_second);
        final RadioButton thirtySecond = dialogView.findViewById(R.id.radioButton_thirty_second);
        final RadioButton sixtySecond = dialogView.findViewById(R.id.radioButton_sixty_second);
        Button OKButton = dialogView.findViewById(R.id.OK);
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
        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                //Activityにtimeの情報を渡したい(return timeとか)
            }
        });
        return builder.create();
    }


}
