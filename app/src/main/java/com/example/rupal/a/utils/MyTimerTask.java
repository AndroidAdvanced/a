package com.example.rupal.a.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.example.rupal.a.activities.MainActivity;

import java.io.InputStream;
import java.util.TimerTask;

/**
 * Created by 123 on 1/21/2018.
 */
public  class MyTimerTask extends TimerTask {

    @Override
    public void run() {

       String URL = "http://www.mg-u.in/ad/" + randomIntFromInterval(1,20) + ".jpg";
       new DownloadImage().execute(URL);
    }

    int randomIntFromInterval(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    Bitmap bitmap = null;

    // DownloadImage AsyncTask
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            // Close progressdialog
            if(bitmap != null) {
                onPost();
            }
        }
    }

    void onPost() {
        System.out.println("TAGTAG Run");
        if(Constants.mainActivity != null) {
            System.out.println("TAGTAG Main");

            Constants.mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseMainActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdMainActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
                    Constants.btnAdMainActivity.setVisibility(View.VISIBLE);
                }
            });
        } else if(Constants.q_categoryActivity != null) {
            System.out.println("TAGTAG Q Category");

            Constants.q_categoryActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseQCategoryActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdQCategoryActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdQCategoryActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));

                }
            });
        } else if(Constants.questionActivity != null) {
            System.out.println("TAGTAG QuestionActivity");

            Constants.questionActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Constants.btnCloseQQuestionActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdQQuestionActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdQQuestionActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));

                }
            });
        } else if(Constants.levelActivity != null) {

            System.out.println("TAGTAG LevelAc");

            Constants.levelActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseLevelActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdLevelActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdLevelActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
                }
            });
        } else if(Constants.resultActivity != null) {

            System.out.println("TAGTAG Result");

            Constants.resultActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseResultActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdResultActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdResultActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));

                }
            });
        }  else if(Constants.feedBackActivity != null) {

            System.out.println("TAGTAG FeedBack");

            Constants.feedBackActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseFeedbackActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdFeedbackActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdFeedbackActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));

                }
            });
        }  else if(Constants.jobActivity != null) {

            System.out.println("TAGTAG Job");

            Constants.jobActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseJobActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdJobActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdJobActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));

                }
            });
        }  else if(Constants.reviewActivity != null) {

            System.out.println("TAGTAG Review");

            Constants.reviewActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseReviewActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdReviewActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdReviewActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));

                }
            });

        } else if(Constants.luckyDActivity != null) {

            System.out.println("TAGTAG Review");
            Constants.luckyDActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseluckyDActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdluckyDActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdluckyDActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));

                }
            });
        } else if(Constants.settingsActivity != null) {

            System.out.println("TAGTAG Review");
            Constants.settingsActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Constants.btnCloseSettingsActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdSettingsActivity.setVisibility(View.VISIBLE);
                    Constants.btnAdSettingsActivity.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
                }
            });
        }

    }
}