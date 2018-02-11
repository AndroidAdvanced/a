package com.example.rupal.a.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.rupal.a.DatabaseHandler;
import com.example.rupal.a.R;
import com.example.rupal.a.activities.FeedbackScreen;
import com.example.rupal.a.activities.JobScreen;
import com.example.rupal.a.activities.LevelScreen;
import com.example.rupal.a.activities.LuckyDraw;
import com.example.rupal.a.activities.MainActivity;
import com.example.rupal.a.activities.Question_Category;
import com.example.rupal.a.activities.QuestionsActivity;
import com.example.rupal.a.activities.ResultActivity;
import com.example.rupal.a.activities.ReviewQuestions;
import com.example.rupal.a.activities.SettingsActivity;
import com.example.rupal.a.model.Science;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constants {

    public static final String CATEGORY = "category";
    private static final String BASE_URL = "http://www.mg-u.in/";
    public static int SPLASH_SCREEN_DELAY = 3000;
    private static Constants instance;
    private DatabaseHandler db;
    private Retrofit retrofit;

    public static List<Science> listQuestions;

    public  Typeface fontRegular, fontBold, fontLight, fontMedium;

    public static Button btnAdMainActivity, btnCloseMainActivity, btnAdQCategoryActivity, btnCloseQCategoryActivity,
            btnAdQQuestionActivity, btnCloseQQuestionActivity,  btnAdLevelActivity, btnCloseLevelActivity,
            btnAdResultActivity, btnCloseResultActivity,
            btnAdFeedbackActivity, btnCloseFeedbackActivity,
            btnAdJobActivity, btnCloseJobActivity,
            btnAdReviewActivity, btnCloseReviewActivity,
            btnAdluckyDActivity, btnCloseluckyDActivity,
            btnAdSettingsActivity, btnCloseSettingsActivity;

    public static MainActivity mainActivity;
    public static Question_Category q_categoryActivity;
    public static QuestionsActivity questionActivity;
    public static LevelScreen levelActivity;
    public static ResultActivity resultActivity;
    public static FeedbackScreen feedBackActivity;
    public static JobScreen jobActivity;
    public static ReviewQuestions reviewActivity;
    public static LuckyDraw luckyDActivity;
    public static SettingsActivity settingsActivity;

    private Constants() {

    }

    public static Constants getInstance() {
        if (instance == null) {
            instance = new Constants();
        }
        return instance;
    }

    public void getResources(Context mContext) {
        if(fontRegular == null) {
            fontRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
        }
        if(fontBold == null) {
            fontBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Bold.ttf");
        }
        if(fontMedium == null) {
            fontMedium = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf");
        }
        if(fontLight == null) {
            fontLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor).build();
           Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public DatabaseHandler getDbObject(Context context) {
        if (db == null) {
            db = new DatabaseHandler(context);
        }
        return db;
    }

    public void showAlert(String msg, Context context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                context.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void showLog(String message) {
        Log.e("MGU", "MGU: " + message);
    }

    public void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
