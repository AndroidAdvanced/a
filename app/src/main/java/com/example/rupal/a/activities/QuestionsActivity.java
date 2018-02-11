package com.example.rupal.a.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.rupal.a.DatabaseHandler;
import com.example.rupal.a.R;
import com.example.rupal.a.adapter.QuestionsAdapter;
import com.example.rupal.a.data.ApiInterface;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.model.Science;
import com.example.rupal.a.repo.ScienceRepo;
import com.example.rupal.a.repo.WishScienceRepo;
import com.example.rupal.a.utils.Constants;

public class QuestionsActivity extends BaseActivity implements ViewPager.OnPageChangeListener, QuestionsAdapter.OnAnswerGivenListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_question_no)
    AppCompatTextView tvQuestionNo;
    @BindView(R.id.chronometer)
    Chronometer chronometer;
    @BindView(R.id.iv_info)
    ImageView ivInfo;
    @BindView(R.id.iv_settings)
    ImageView ivSettings;

    WishScienceRepo listrep = null;

    ScienceRepo itemrep = null;

    public static HashMap<Integer, Integer> listAnswer = new HashMap<Integer, Integer>();
    public static HashMap<Integer, String> listWrongAnswer = new HashMap<Integer, String>();

    int totalQuestionsAnswered = 0, totalCorrectAnswers = 0;

    @SuppressWarnings("FieldCanBeLocal")
    private QuestionsAdapter adapter;
    public static List<Science> listQuestions = new ArrayList<>();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Integer> hashMap = new HashMap<>();

    @OnClick(R.id.iv_info)
    public void onInfoClicked(View view) {
        startingActivity(InfoScreen.class, true);
    }


    @OnClick(R.id.iv_settings)
    public void onSettingsClicked(View view) {
        Constants.getInstance().showToast("on Settings Clicked", QuestionsActivity.this);
    }

    @OnClick(R.id.iv_next)
    public void onNextClicked(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    @OnClick(R.id.iv_previous)
    public void onPreviousClicked(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    @OnClick(R.id.btn_finish)
    public void onSubmitClicked(View view) {
        startingResultActivity();
    }

    @OnClick(R.id.btn_submit)
    public void onFinishClicked(View view) {
        new MaterialDialog.Builder(this)
                .content("Are you really want to quit this session?")
                .positiveText("YES")
                .negativeText("NO")
                .backgroundColor(getResources().getColor(R.color.white)).contentColorRes(R.color.black)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }

    Intent intent;
    int load_index;

    List<String> listQuestionNumber;

    public static int currentQuestion;

    private ProgressDialog progressDialog;

    @Override
    protected void onResume() {
        super.onResume();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");

        Constants.btnAdQQuestionActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseQQuestionActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.questionActivity = QuestionsActivity.this;

        Constants.btnCloseQQuestionActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdQQuestionActivity.setVisibility(View.GONE);
                Constants.btnCloseQQuestionActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdQQuestionActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdQQuestionActivity.setVisibility(View.GONE);
                Constants.btnCloseQQuestionActivity.setVisibility(View.GONE);
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
        Constants.btnAdQQuestionActivity= null;
        Constants.btnCloseQQuestionActivity  = null;
        Constants.questionActivity = null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.questions_layout);

        ButterKnife.bind(this);
        ivInfo.setImageResource(R.drawable.info_header);
        ivSettings.setImageResource(R.drawable.settings);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        chronometer.start();

        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        initDB();

        listAnswer.clear();

        for(int i = 0 ; i <= 20; i++) {
            listAnswer.put(i,-1);
        }

        ApiInterface apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);
        intent = getIntent();

        DatabaseHandler dbHelper = new DatabaseHandler(this);
        dbHelper.getLevelCount(intent.getStringExtra(Constants.CATEGORY));

        listQuestions = getData(intent.getStringExtra(Constants.CATEGORY));
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        load_index = pref.getInt("load_index"+intent.getStringExtra(Constants.CATEGORY), 0);

        listQuestionNumber = new ArrayList<String>();

        int start_index = intent.getIntExtra("start_index",0);

        for(int i = 1 ; i <= 21; i++) {
            listQuestionNumber.add(i+"");
        }

        if(listQuestions.size() <= (start_index + 20)) {
            listQuestions = listQuestions.subList(start_index,listQuestions.size());
        } else {
            listQuestions = listQuestions.subList(start_index,(start_index + 20));
        }

      //  Collections.shuffle(listQuestions);

        loadViewPagerData();

        currentQuestion = Integer.parseInt(listQuestionNumber.get(0));
        tvQuestionNo.setText(listQuestionNumber.get(0) + "/"+(listQuestions.size()));

        Constants.getInstance().getResources(this);

        tvQuestionNo.setTypeface(Constants.getInstance().fontBold);
    }


    List<Science> getData(String category) {
        List<Science> items = (List<Science>) itemrep.findAll(category, "");
        return items;
    }

    private void loadViewPagerData() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        adapter = new QuestionsAdapter(this, listQuestions, progressDialog, getIntent().getStringExtra(Constants.CATEGORY), pref.getInt("textsize",2));
        adapter.setOnAnswerGivenListener(this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPageSelected(int position) {
        tvQuestionNo.setText(listQuestionNumber.get(position) + "/"+(listQuestions.size()));
        currentQuestion = Integer.parseInt(listQuestionNumber.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onAnswerGiven(final int position, int selectedOption, boolean moveToNext, boolean answeredCorrectly, final List<AppCompatImageView> listResult) {

        hashMap.put(position, selectedOption);
        totalQuestionsAnswered++;
        if (answeredCorrectly) {
            totalCorrectAnswers++;
        }

        if (currentQuestion == listQuestions.size()) {
            startingResultActivity();
        } else {
            if (moveToNext) {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(currentQuestion);
                        if(listResult != null){
                            for(int i = 0 ; i < listResult.size() ;i++) {
                                listResult.get(i).setVisibility(View.INVISIBLE);
                            }
                        }

                    }
        }, 130);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void startingResultActivity() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("total_questions", String.valueOf(listQuestions.size()));
        intent.putExtra("total_time_taken", chronometer.getText().toString());
        intent.putExtra("total_attempted", String.valueOf(totalQuestionsAnswered));
        intent.putExtra("total_correct", String.valueOf(totalCorrectAnswers));
        intent.putExtra("category", getIntent().getStringExtra(Constants.CATEGORY));
        intent.putExtra("level_number", getIntent().getIntExtra("level_number", 0));
        startingActivity(intent, true);
    }

    private void initDB() {
        DatabaseManager.init(this);
        listrep = new WishScienceRepo(this);
        itemrep = new ScienceRepo(this);
    }
}