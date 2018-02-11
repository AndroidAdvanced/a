package com.example.rupal.a.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.rupal.a.R;
import com.example.rupal.a.adapter.ReviewQuestionAdapter;
import com.example.rupal.a.data.ReviewQuestion;
import com.example.rupal.a.model.Science;
import com.example.rupal.a.utils.Constants;

/**
 * Created by Rupal on 12/15/2017.
 */
public class ReviewQuestions extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    private ReviewQuestionAdapter mAdapter;

    List<ReviewQuestion> listReviewQuestion;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.review_questions);

        ButterKnife.bind(this);
        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        iv_back.setImageDrawable(upArrow);

        prepareQuestionData();

        mAdapter = new ReviewQuestionAdapter(listReviewQuestion, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);
    }

    private void prepareQuestionData() {

        ReviewQuestion reviewQuestion;

        listReviewQuestion = new ArrayList<ReviewQuestion>();

        if(QuestionsActivity.listQuestions == null) {
            return;
        }

        List<Science> listQuestions = QuestionsActivity.listQuestions;

        for(int  i = 0 ; i < listQuestions.size() ; i++) {
            reviewQuestion = new ReviewQuestion(listQuestions.get(i).getQuestion(), listQuestions.get(i).getAnswer());
            listReviewQuestion.add(reviewQuestion);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Constants.btnAdReviewActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseReviewActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.reviewActivity = ReviewQuestions.this;

        Constants.btnCloseReviewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdReviewActivity.setVisibility(View.GONE);
                Constants.btnCloseReviewActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdReviewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdReviewActivity.setVisibility(View.GONE);
                Constants.btnCloseReviewActivity.setVisibility(View.GONE);
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
        Constants.btnAdReviewActivity= null;
        Constants.btnCloseReviewActivity  = null;
        Constants.reviewActivity = null;
    }
}
