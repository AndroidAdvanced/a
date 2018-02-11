package com.example.rupal.a.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.rupal.a.DatabaseHandler;
import com.example.rupal.a.R;
import com.example.rupal.a.utils.Constants;
import com.example.rupal.a.utils.UserScore;

public class ResultActivity extends BaseActivity {


    @BindView(R.id.txtTotalQuestion_Data)
    TextView txtTotalQuestion_Data;

    @BindView(R.id.txtTotalTime_Data)
    TextView txtTotalTime_Data;

    @BindView(R.id.txtAttempted_Data)
    TextView txtAttempted_Data;

    @BindView(R.id.txtCorrect_Data)
    TextView txtCorrect_Data;

    @OnClick(R.id.btnReviewQ)
    public void onReviewQClicked(View view) {
        Intent intent = new Intent(this, ReviewQuestions.class);
        intent.putExtra("category", getIntent().getStringExtra(Constants.CATEGORY));
        startingActivity(intent, false);
    }

    @OnClick(R.id.btnLaunchQuiz)
    public void onLaunchQuizClicked(View view) {
        Constants.getInstance().showToast("onLaunchQuizClicked", this);
    }

    @OnClick(R.id.cancel)
    public void onCancelClicked(View view) {
        Intent intent = new Intent(this, LevelScreen.class);
        intent.putExtra("category", getIntent().getStringExtra(Constants.CATEGORY));
        startingActivity(intent, true);
    }

    @Override
    public void onResume() {
        super.onResume();

        Constants.btnAdResultActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseResultActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.resultActivity = ResultActivity.this;

        Constants.btnCloseResultActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdResultActivity.setVisibility(View.GONE);
                Constants.btnCloseResultActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdResultActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdResultActivity.setVisibility(View.GONE);
                Constants.btnCloseResultActivity.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://mg-u.in"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.btnAdResultActivity= null;
        Constants.btnCloseResultActivity  = null;
        Constants.resultActivity = null;
    }

    int  totalCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        ButterKnife.bind(this);
        Intent intent = getIntent();

        totalCorrect = Integer.valueOf(intent.getStringExtra("total_correct"));

        txtTotalQuestion_Data.setText(intent.getStringExtra("total_questions"));

        txtTotalTime_Data.setText(intent.getStringExtra("total_time_taken"));

        txtAttempted_Data.setText(intent.getStringExtra("total_attempted"));

        txtCorrect_Data.setText(intent.getStringExtra("total_correct"));

        txtTotalQuestion_Data.setText(intent.getStringExtra("total_questions"));

        txtCorrect_Data.setText(intent.getStringExtra("total_correct"));


            int total_correct = Integer.valueOf(intent.getStringExtra("total_correct"));
            int total_questions = Integer.valueOf(intent.getStringExtra("total_questions"));

            int level_number = intent.getIntExtra("level_number",0);

            UserScore userScore = new UserScore();
            userScore.setLevel_number(level_number);
            userScore.setCategory(intent.getStringExtra("category"));
            userScore.setScore((total_correct*100)/20);

            DatabaseHandler dbHandler = new DatabaseHandler(this);

        if(dbHandler.getLevelScoreAlreadyExist(intent.getStringExtra("category"), level_number)){
            dbHandler.updateLevelScore(userScore);
        } else {
            dbHandler.addLevelScore(userScore);
        }

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();

        edit.putLong("questions_attempted", Long.valueOf(intent.getStringExtra("total_attempted")));
        edit.putLong("total_time", Long.valueOf(intent.getStringExtra("total_attempted")));
        edit.commit();
    }
}
