package com.example.rupal.a.fragments;
/**
 * Created by 123 on 1/30/2018.
 */
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.rupal.a.R;
import com.example.rupal.a.activities.JobScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDialogFragment extends DialogFragment  {

    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.splashScreenTheme);
    }

    JobScreen jobActivity;

    public void sendActivity(JobScreen jobActivity) {
        this.jobActivity = jobActivity;
    }

    public MyDialogFragment() {

    }

    View view;

    AppCompatCheckBox chkGraduation, chkbsc, chkbcom;
    com.example.rupal.a.model.CustomCheckBox chkca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dialog, container);
        ButterKnife.bind(view);

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked();

                if(jobActivity != null) {
                    jobActivity.getJob("salary","asc");
                } else {

                }
                dismiss();
            }
        });

        chkGraduation = (AppCompatCheckBox) view.findViewById(R.id.chkGraduation);
        chkbsc = (AppCompatCheckBox) view.findViewById(R.id.chkbsc);
        chkbcom = (AppCompatCheckBox) view.findViewById(R.id.chkbcom);
        chkca = (com.example.rupal.a.model.CustomCheckBox) view.findViewById(R.id.chkca);
        chkca.setChecked(true);

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void isChecked() {

        if(chkGraduation.isChecked()) {
            JobScreen.listChecked.add("Graduation");
        }
        if(chkbsc.isChecked()) {
            JobScreen.listChecked.add("B.sc");
        }
        if(chkbcom.isChecked()) {
            JobScreen.listChecked.add("B.com");
        }
        if(chkca.isChecked()) {
            JobScreen.listChecked.add("MCA");
        }
    }
}