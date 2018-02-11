package com.example.rupal.a.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.rupal.a.R;
import com.example.rupal.a.DatabaseHandler;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.model.Categoryname;
import com.example.rupal.a.repo.CategoryItemRepo;
import com.example.rupal.a.utils.Constants;

public class Question_Category extends BaseActivity implements View.OnClickListener {

    DatabaseHandler db;
    List<Categoryname> listCategories;

    HashMap<Integer,String> hash = new HashMap<Integer, String>();

    @BindView(R.id.rl_fragment_load)
    RelativeLayout rlFragmentLoad;

    boolean isOnCreateCalled = false;

    CategoryItemRepo irepo = null;

    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_categories);

        ButterKnife.bind(this);
        db = Constants.getInstance().getDbObject(this);

        this.initDB();

        isOnCreateCalled = true;
    }

    @OnClick(R.id.iv_back)
    public void onBackClicked(View view) {
        finish();
    }

    private void initDB()
    {
        DatabaseManager.init(this);
        irepo = new CategoryItemRepo(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        iv_back.setImageDrawable(upArrow);

            Constants.btnAdQCategoryActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
            Constants.btnCloseQCategoryActivity  =  (Button) findViewById(R.id.btnClose);
            Constants.q_categoryActivity = Question_Category.this;

            Constants.btnCloseQCategoryActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.btnAdQCategoryActivity.setVisibility(View.GONE);
                    Constants.btnCloseQCategoryActivity.setVisibility(View.GONE);
                }
            });
        Constants.btnAdQCategoryActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdQCategoryActivity.setVisibility(View.GONE);
                Constants.btnCloseQCategoryActivity.setVisibility(View.GONE);
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
        Constants.btnAdQCategoryActivity= null;
        Constants.btnCloseQCategoryActivity  = null;
        Constants.q_categoryActivity = null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isOnCreateCalled) {

            loadDemoData(null);

            GridLayoutManager llm = new GridLayoutManager(this, 2);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
        }
        isOnCreateCalled = false;
    }

    private void loadDemoData(List<Categoryname> list) {

        listCategories = new ArrayList<>();
        ArrayList<String> listCategory = new ArrayList<String>();

        List<Categoryname> items = (List<Categoryname>) irepo.findAll();

        Categoryname model;

        for(Categoryname a : items)
        {
            model = new Categoryname();
            model.setCatname(a.getCatname());
            model.setCatId(a.getCatId());

            listCategories.add(model);
            listCategory.add(a.getCatname());
        }

        LinearLayout relativeTop;

        if(listCategory.size() % 2 != 0) {
            listCategory.add("");
        }

        relativeTop = (LinearLayout) findViewById(R.id.linearTop);

        int id = 0;

        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        int dpLinear = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 190, this.getResources().getDisplayMetrics());
        int dpView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, this.getResources().getDisplayMetrics());

        for(int i = 0 ; i < listCategory.size() /2  ; i++) {

            LinearLayout linear = new LinearLayout(this);
            linear.setOrientation(LinearLayout.HORIZONTAL);
            id++;
            linear.setId(id);
            linear.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dpLinear,0.9f);
            relativeTop.addView(linear, lp);

            View view = new View(this);
            lp = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dpView);
            relativeTop.addView(view, lp);
        }

        LinearLayout linear = null;
        int catid=0;
        int idChangeColor = 1;
        String color = "white";

        for(int i = 0 ; i < listCategory.size() ; i++) {

            LinearLayout.LayoutParams lp;
            if(i%2 == 0) {
                catid ++;
                idChangeColor = 1;
                linear =(LinearLayout) findViewById(catid);
            }

            TextView btn = new TextView(this);
            btn.setText(listCategory.get(i));
            btn.setTextColor(getResources().getColor(R.color.white));
            btn.setGravity(Gravity.CENTER);
            btn.setTextSize(20);
            if(color.equals("white")) {
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.FILL_PARENT,0.85f);
                btn.setBackgroundColor(getResources().getColor(R.color.apptheme));
            } else{
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.FILL_PARENT,0.8f);
                btn.setBackgroundColor(getResources().getColor(R.color.dark_blue));
            }

            btn.setOnClickListener(this);

            id++;
            hash.put(id,listCategory.get(i));
            btn.setId(id);
            linear.addView(btn, lp);

            idChangeColor = idChangeColor + 1;

            if(idChangeColor == 2) {
                idChangeColor = 0;
                if(color.equals("white")) {
                    color = "blue;";
                } else{
                    color = "white";
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        if(hash.get(view.getId()).equals("")) {

        } else {
            Intent intent = new Intent(this, LevelScreen.class);
            intent.putExtra(Constants.CATEGORY, hash.get(view.getId()));
            startingActivity(intent, false);
        }
    }
}

