package com.example.rupal.a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class Question_Job extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.job);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Button btnQue = (Button) findViewById(R.id.btnQue);
//        btnQue.setOnClickListener(this);
//
//        Button btnJob = (Button) findViewById(R.id.btnJob);
//        btnJob.setOnClickListener(this);
//
//        Button btnInfo = (Button) findViewById(R.id.btnInfo);
//        btnInfo.setOnClickListener(this);

    }
}

