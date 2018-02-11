package com.example.rupal.a.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.rupal.a.DatabaseHandler;
import com.example.rupal.a.R;
import com.example.rupal.a.data.ApiInterface;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.model.FetchQuestions;
import com.example.rupal.a.model.Science;
import com.example.rupal.a.repo.ScienceRepo;
import com.example.rupal.a.repo.WishScienceRepo;
import com.example.rupal.a.utils.Constants;
import com.example.rupal.a.utils.UserScore;

/**
 * Created by Rupal on 10/22/2017.
 */
public class LevelScreen  extends BaseActivity {

    @BindView(R.id.txtTitle)
    AppCompatTextView txtTitle;

    @BindView(R.id.iv_back)
    AppCompatImageView btnBack;



    private List<Science> listQuestions = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.level_screen);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        txtTitle.setText(intent.getStringExtra(Constants.CATEGORY));

        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        btnBack.setImageDrawable(upArrow);

        initDB();
        setData();
    }

    @Override
    public void onResume() {
        super.onResume();

        Constants.btnAdLevelActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseLevelActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.levelActivity = LevelScreen.this;

        Constants.btnCloseLevelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdLevelActivity.setVisibility(View.GONE);
                Constants.btnCloseLevelActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdLevelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdLevelActivity.setVisibility(View.GONE);
                Constants.btnCloseLevelActivity.setVisibility(View.GONE);
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
        Constants.btnAdLevelActivity= null;
        Constants.btnCloseLevelActivity  = null;
        Constants.levelActivity = null;
    }

    @OnClick(R.id.iv_back)
    public void onBackClicked(View view) {
        finish();
    }

    void setData() {

        listQuestions = getData(getIntent().getStringExtra(Constants.CATEGORY));
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        int levelCount = 0;
        if(listQuestions != null) {
            levelCount = listQuestions.size() / 20;
            if(listQuestions.size() % 20 != 0) {
                levelCount = levelCount + 1;
            }
        }

        if(pref.getInt("dbversion",0) != 100 ) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("dbversion", 100);
            editor.commit();
        }

        DatabaseHandler dbHandler = new DatabaseHandler(getBaseContext());
        ArrayList<UserScore>  listUserScore = dbHandler.getLevelScore(getIntent().getStringExtra(Constants.CATEGORY));

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getBaseContext(),LevelScreen.this, levelCount, listQuestions, getIntent().getStringExtra(Constants.CATEGORY),listUserScore));
    }

    ScienceRepo itemrep = null;

    List<Science> getData(String category) {
        List<Science> items = (List<Science>) itemrep.findAll(category, "");
        return items;
    }

    private void initDB() {
        DatabaseManager.init(this);
        itemrep = new ScienceRepo(this);
    }

    boolean isTablet(){

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);

        if (diagonalInches>=6.5){
            // 6.5inch device or bigger
            return true;
        } else {
            return false;
            // smaller device
        }
    }
}
