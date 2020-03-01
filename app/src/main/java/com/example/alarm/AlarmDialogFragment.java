package com.example.alarm;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
    private long time = 0;
    private DialogInterface.OnDismissListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();
        if (null == activity) {
            throw new IllegalStateException();
        }
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
                    time = 10000;
                }
                if (checkedId == thirtySecond.getId()) {
                    time = 30000;
                }
                if (checkedId == sixtySecond.getId()) {
                    time = 60000;
                }
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.setTime(getActivity(), SharedPreferencesUtil.KEY_TIME, time);
                dismiss();
            }
        });

        return builder.create();
    }

    void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onDismiss(dialog);
    }
}
