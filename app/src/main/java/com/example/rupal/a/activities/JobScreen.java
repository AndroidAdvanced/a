package com.example.rupal.a.activities;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.rupal.a.R;
import com.example.rupal.a.adapter.JobSearchResultAdapter;
import com.example.rupal.a.data.ApiInterface;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.fragments.MyDialogFragment;
import com.example.rupal.a.model.Job12;
import com.example.rupal.a.repo.JobItemRepo;
import com.example.rupal.a.utils.Constants;
import com.example.rupal.a.utils.DividerItemDecoration;

public class JobScreen extends BaseActivity {

    @BindView(R.id.recyclerViewResult)
    RecyclerView mRecyclerView;

    ApiInterface apiInterface;

    JobItemRepo irepo = null;

    public static List<String> listChecked = new ArrayList<String>();
    public static List<String> listCheckedCity = new ArrayList<String>();

    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    @BindView(R.id.btnSort)
    AppCompatImageView btnSort;

    @BindView(R.id.txtTitle)
    AppCompatTextView txtTitle;

    @OnClick(R.id.btnSort)
    public void onFilterClicked(View view) {
       // showPictureialog();

        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_edit_name");

        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        MyDialogFragment editNameDialog = new MyDialogFragment();
        editNameDialog.sendActivity(JobScreen.this);
        editNameDialog.show(manager, "fragment_edit_name");
    }


    @OnClick(R.id.back)
    public void onBackClicked(View view) {
        finish();
    }

    JobSearchResultAdapter jobSearchResultAdapter;

    List<Job12> listJob;

    public void getJob(String filter_name, String filter_order) {

       listJob = (List<Job12>) irepo.findAll(filter_name, filter_order);

        List<Job12> listTemp = new ArrayList<Job12>();

        if(listChecked == null && listCheckedCity == null) {
            listTemp = listJob;
        } else {

            if(listChecked != null) {

                for(int i = 0 ; i < listChecked.size() ; i++) {

                    for(int k = 0 ; k < listJob.size() ; k++) {
                        if(listJob.get(k).getQualification() != null && listJob.get(k).getQualification().equals(listChecked.get(i))) {
                            listTemp.add(listJob.get(k));
                        }
                    }
                }
            }

            if(listChecked != null && listCheckedCity != null) {

                for(int i = 0 ; i < listCheckedCity.size() ; i++) {

                    for(int k = 0 ; k < listJob.size() ; k++) {
                        if(listJob.get(k).getPlace() != null && listJob.get(k).getPlace().equals(listCheckedCity.get(i))) {

                            boolean isFound = false;

                            for(int j = 0 ; j < listTemp.size() ; j++) {
                                if(listJob.get(k).getJobId() == listTemp.get(j).getJobId()) {
                                    isFound = true;
                                    break;
                                }
                            }
                            if(!isFound) {
                                listTemp.add(listJob.get(k));
                            }
                        }
                    }
                }

            } else {
                if(listCheckedCity != null) {
                    for(int i = 0 ; i < listCheckedCity.size() ; i++) {

                        for(int k = 0 ; k < listJob.size() ; k++) {
                            if(listJob.get(k).getPlace() != null && listJob.get(k).getPlace().equals(listCheckedCity.get(i))) {
                                listTemp.add(listJob.get(k));
                            }
                        }
                    }
                }
            }
        }

        jobSearchResultAdapter = new JobSearchResultAdapter(listTemp);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(jobSearchResultAdapter);
        jobSearchResultAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.job);

        ButterKnife.bind(this);

        initDB();

        Constants.getInstance().getResources(this);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String filter_name = pref.getString("job_filter_name","salary");
        String filter_order = pref.getString("job_filter_order","asc");

        Constants.getInstance().getResources(this);

        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        iv_back.setImageDrawable(upArrow);

        getJob(filter_name, filter_order);
    }

    private void initDB() {
        DatabaseManager.init(this);
        irepo = new JobItemRepo(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        iv_back.setImageDrawable(upArrow);

        txtTitle.setTypeface(Constants.getInstance().fontBold);
    }

    @Override
    public void onResume() {
        super.onResume();

        Constants.btnAdJobActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseJobActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.jobActivity = JobScreen.this;

        Constants.btnCloseJobActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdJobActivity.setVisibility(View.GONE);
                Constants.btnCloseJobActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdJobActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdJobActivity.setVisibility(View.GONE);
                Constants.btnCloseJobActivity.setVisibility(View.GONE);
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
}

