package com.example.rupal.a.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.rupal.a.DatabaseHandler;
import com.example.rupal.a.R;

public class Question_Exam extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.question_exam);

        Intent intent = getIntent();
        String category = null;
        if(intent != null) {
            category = intent.getStringExtra("category");
        }

        TextView txtExam = (TextView) findViewById(R.id.txtQuestion);
        txtExam.setText(category+"");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {

    }
}

