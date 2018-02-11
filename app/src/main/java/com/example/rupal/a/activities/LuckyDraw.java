package com.example.rupal.a.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.example.rupal.a.R;
import com.example.rupal.a.data.ApiInterface;
import com.example.rupal.a.utils.Constants;

public class LuckyDraw extends BaseActivity {

    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtAge)
    EditText edtAge;
    @BindView(R.id.edtMobile)
    EditText edtMobile;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtComment)
    EditText edtComment;

    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @SuppressWarnings("unused")
    @OnClick(R.id.btnSubmit)
    public void onSubmitClicked(View view) {

        if (Constants.getInstance().isNetworkAvailable(this)) {
        } else {
            Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), this);
            return;
        }

        ArrayList<String> submitData = new ArrayList<>();
        String name = edtName.getText().toString();
        String age = edtAge.getText().toString();
        String mobile = edtMobile.getText().toString();
        String email = edtEmail.getText().toString();
        String comment = edtComment.getText().toString();

        if (Constants.getInstance().isNetworkAvailable(LuckyDraw.this)) {
            if (name.length() == 0) {
                edtName.setError("Field cannot be left blank.");
                return;
            } else if (age.length() == 0) {
                edtAge.setError("Field cannot be left blank.");
                return;
            } else if (mobile.length() == 0) {
                edtMobile.setError("Field cannot be left blank.");
                return;
            } else if (email.length() == 0) {
                edtEmail.setError("Field cannot be left blank.");
                return;
            } else if (comment.length() == 0) {
                edtComment.setError("Field cannot be left blank.");
                return;
            }
            Observable<String> quotes = apiInterface.postLuckyDraw(name, age, mobile, email, comment);
            progressDialog.show();
            quotes
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String loginResponse) {
                            //Constants.getInstance().showLog(loginResponse);
                        }
                    });

            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    LuckyDraw.this.finish();
                }
            }, 2000);

        } else {
            Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), LuckyDraw.this);
        }
    }

    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    @BindView(R.id.tv_feedback)
    AppCompatTextView tv_feedback;

    @BindView(R.id.btnSubmit)
    AppCompatButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.luckydraw);
        ButterKnife.bind(this);

        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        iv_back.setImageDrawable(upArrow);

        tv_feedback.setTypeface(Constants.getInstance().fontBold);

        btnSubmit.setTypeface(Constants.getInstance().fontMedium);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.iv_back)
    public void onBackClicked(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
    }

    public static class InputStreamToStringExample {

        public static void main(String[] args) throws IOException {

            // intilize an InputStream
            InputStream is =
                    new ByteArrayInputStream("file content..blah blah".getBytes());

            String result = getStringFromInputStream(is);
            System.out.println(result);
        }

        // convert InputStream to String
        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Constants.btnAdluckyDActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseluckyDActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.luckyDActivity = LuckyDraw.this;

        Constants.btnCloseluckyDActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdluckyDActivity.setVisibility(View.GONE);
                Constants.btnCloseluckyDActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdluckyDActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdluckyDActivity.setVisibility(View.GONE);
                Constants.btnCloseluckyDActivity.setVisibility(View.GONE);
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
        Constants.btnAdluckyDActivity = null;
        Constants.btnCloseluckyDActivity  = null;
        Constants.luckyDActivity = null;
    }
}

