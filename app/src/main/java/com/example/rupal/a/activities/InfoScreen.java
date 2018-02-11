package com.example.rupal.a.activities;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rupal.a.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoScreen extends BaseActivity {

    @BindView(R.id.txt_questions_attempted)
    AppCompatTextView txt_questions_attempted;

    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        ButterKnife.bind(this);
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.back)
    public void onBackClicked(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        iv_back.setImageDrawable(upArrow);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);;

        txt_questions_attempted.setText(getResources().getString(R.string.total_attempted)+ " : " + pref.getLong("questions_attempted",0)  );

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnFeedBack)
    public void onFeedbackClicked(View view) {
        startingActivity(FeedbackScreen.class, false);
/*
        if (Constants.getInstance().isNetworkAvailable(InfoScreen.this)) {
            startingActivity(FeedbackScreen.class, false);
        } else {
            Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), InfoScreen.this);
        }*/
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnLuckyDraw)
    public void onLuckDrawClicked(View view) {
        startingActivity(LuckyDraw.class, false);
      /*  if (Constants.getInstance().isNetworkAvailable(InfoScreen.this)) {
            startingActivity(LuckyDraw.class, false);
        } else {
            Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), InfoScreen.this);
        }*/
    }
}

