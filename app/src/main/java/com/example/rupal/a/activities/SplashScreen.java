package com.example.rupal.a.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rupal.a.DatabaseHandler;
import com.example.rupal.a.R;
import com.example.rupal.a.data.ApiInterface;
import com.example.rupal.a.data.GetQuotes;
import com.example.rupal.a.utils.Constants;

public class SplashScreen extends BaseActivity {
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splashscreen);

        db = Constants.getInstance().getDbObject(this);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("Launch_Count" , pref.getInt("Launch_Count", 0)+1);
        if(pref.getBoolean("RemindLater", false)) {
            edit.putInt("Launch_CountRemindLater" , pref.getInt("Launch_CountRemindLater", 0)+1);
        }
        edit.commit();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

        if (Constants.getInstance().isNetworkAvailable(this)) {
            ApiInterface apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);
            Observable<GetQuotes> quotes = apiInterface.getQuotes();
            quotes
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GetQuotes>() {
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
                        public void onNext(GetQuotes loginResponse) {
                            Constants.getInstance().showLog(loginResponse.toString());
                        }
                    });
            if (db.getQuotesCount() == 0) {
                TextView txtQuote = (TextView) findViewById(R.id.txtQuote);
                txtQuote.setText(getResources().getString(R.string.first_quote));
                txtQuote.setTextAppearance(this, android.R.style.TextAppearance_Large * 17);
            } else {

                TextView txtQuote = (TextView) findViewById(R.id.txtQuote);
                txtQuote.setText(db.getQuote(randomIntFromInterval(1, db.getQuotesCount())) + "");
                txtQuote.setTextAppearance(this, android.R.style.TextAppearance_Large * 17);
            }
        }

      (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
               startingActivity(MainActivity.class, true);
            }
        }, Constants.SPLASH_SCREEN_DELAY);
    }

    @SuppressWarnings("SameParameterValue")
    int randomIntFromInterval(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}

