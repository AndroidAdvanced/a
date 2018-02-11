package com.example.rupal.a.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rupal.a.R;
import com.example.rupal.a.data.ApiInterface;
import com.example.rupal.a.utils.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FeedbackScreen extends BaseActivity {

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtMobile)
    EditText edtMobile;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtComment)
    EditText edtComment;

    @BindView(R.id.btn_feedback)
    AppCompatButton btn_feedback;

    private ApiInterface apiInterface;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        ButterKnife.bind(this);
    }

    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    @BindView(R.id.tv_feedback)
    AppCompatTextView tv_feedback;

    @BindView(R.id.txt_feedback_detail)
    AppCompatTextView txt_feedback_detail;

    @Override
    protected void onStart() {
        super.onStart();

        Constants.getInstance().getResources(this);
        apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");

        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        iv_back.setImageDrawable(upArrow);

        tv_feedback.setTypeface(Constants.getInstance().fontBold);

        btn_feedback.setTypeface(Constants.getInstance().fontMedium);

        txt_feedback_detail.setText(getResources().getString(R.string.feedback_detail));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.iv_back)
    public void onBackClicked(View view) {
        finish();
    }

        @SuppressWarnings("unused")
    @OnClick(R.id.btn_feedback)
    public void onSubmitClicked(View view) {

            if (Constants.getInstance().isNetworkAvailable(this)) {
            } else {
                Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), this);
                return;
            }

        ArrayList<String> submitData = new ArrayList<>();
        String name = edtName.getText().toString();
        String mobile = edtMobile.getText().toString();
        String email = edtEmail.getText().toString();
        String comment = edtComment.getText().toString();

        if (Constants.getInstance().isNetworkAvailable(FeedbackScreen.this)) {
            if (name.length() == 0) {
                edtName.setError("Field cannot be left blank.");
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
            Observable<String> quotes = apiInterface.postFeedBack(name, mobile, email, comment);
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
                    FeedbackScreen.this.finish();
                }
            }, 2000);

        } else {
            Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), FeedbackScreen.this);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

/*    public static class InputStreamToStringExample {

        *//* Inner class to get response *//*
        class AsyncSubmit extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {

            String responseServer;

            @Override
            protected ArrayList<String> doInBackground(ArrayList<String>... voids) {

                ArrayList<String> result = voids[0];

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://mg-u.in/api2/qa/feedback.php");

                try {

                    JSONObject jsonobj = new JSONObject();
                    jsonobj.put("name", result.get(0));
                    jsonobj.put("mobile", result.get(1));
                    jsonobj.put("email", result.get(2));
                    jsonobj.put("comment", result.get(3));

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("req", jsonobj.toString()));

                    // Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

                    // Use UrlEncodedFormEntity to send in proper format which we need
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    InputStream inputStream = response.getEntity().getContent();
                    InputStreamToStringExample str = new InputStreamToStringExample();
                    responseServer = InputStreamToStringExample.getStringFromInputStream(inputStream);
                    // Log.e("response.....", "response -----" + responseServer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<String> aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getBaseContext(), responseServer, Toast.LENGTH_LONG).show();
            }
        }*/

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

        Constants.btnAdFeedbackActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseFeedbackActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.feedBackActivity = FeedbackScreen.this;

        Constants.btnCloseFeedbackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdFeedbackActivity.setVisibility(View.GONE);
                Constants.btnCloseFeedbackActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdFeedbackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdFeedbackActivity.setVisibility(View.GONE);
                Constants.btnCloseFeedbackActivity.setVisibility(View.GONE);
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
        Constants.btnAdFeedbackActivity= null;
        Constants.btnCloseFeedbackActivity  = null;
        Constants.feedBackActivity = null;
    }
}

