package com.example.rupal.a.activities;

import android.app.Dialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rupal.a.utils.MyTimerTask;
import com.example.rupal.a.utils.mJobScheduler;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.example.rupal.a.DatabaseHandler;
import com.example.rupal.a.R;
import com.example.rupal.a.data.ApiInterface;
import com.example.rupal.a.data.GetQuotes;
import com.example.rupal.a.data.Position;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.model.Categoryname;
import com.example.rupal.a.model.FetchQuestions;
import com.example.rupal.a.model.Job12;
import com.example.rupal.a.model.JobScreenModel;
import com.example.rupal.a.model.QuestionsCategories;
import com.example.rupal.a.model.Science;
import com.example.rupal.a.model.WishScience;
import com.example.rupal.a.repo.CategoryItemRepo;
import com.example.rupal.a.repo.JobItemRepo;
import com.example.rupal.a.repo.ScienceRepo;
import com.example.rupal.a.repo.WishScienceRepo;
import com.example.rupal.a.utils.Constants;
import com.example.rupal.a.utils.UserScore;

public class MainActivity extends BaseActivity {

    CategoryItemRepo irepo = null;

    WishScienceRepo listrep = null;
    ScienceRepo itemrep = null;

    CallbackManager callbackManager;

    Button appCompatImageViewAd, btnClose;

    SharedPreferences pref;

    Handler handler = new Handler();

    Runnable runnable;

    String URL = "http://www.androidbegin.com/wp-content/uploads/2013/07/HD-Logo.gif";

    private static final int JOB_ID = 101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainactivity);
        ButterKnife.bind(this);

        Constants.getInstance().getResources(this);

        new DownloadImage().execute(URL);

        initDB();

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        if (pref.getBoolean("isDataBase_Changed", true) == true) {
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("isDataBase_Changed", false);
            edit.commit();
            showIndeterminateProgressDialog(false);
            loadDatabase();
        }

        Timer myTimer = new Timer();
        MyTimerTask myTimerTask= new MyTimerTask();
        myTimer.scheduleAtFixedRate(myTimerTask, 0, 1 * 15000);

        if((pref.getBoolean("RemindLater", false))) {
              if(pref.getInt("Launch_CountRemindLater", 0) == 10 ) {
                  callRateUsDialog();
              }
        } else if(pref.getBoolean("RemindLater", false) == false && pref.getInt("Launch_Count",0)>= 15) {
              callRateUsDialog();
        }
    }

    private void storeImage(Bitmap image) {

        File pictureFile = getOutputMediaFile();

            Log.d("TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());

            File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()+"/FolderName");

            String[] fileNames = null;

            if(path.exists())
            {
                fileNames = path.list();
            }

            for(int i = 0; i < fileNames.length; i++)
            {
                Bitmap mBitmap = BitmapFactory.decodeFile(path.getPath()+"/"+ fileNames[i]);

                //appCompatImageView1.setImageBitmap(mBitmap);

                ///Now set this bitmap on imageview
            }

        try {

            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/FolderName");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! myDir.exists()){
            if (! myDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(myDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    MaterialDialog m;
    private void showIndeterminateProgressDialog(boolean horizontal) {
        m = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .titleColorRes(android.R.color.black)
                .contentColorRes(android.R.color.black)
                .progress(true, 0)
                .backgroundColor(Color.WHITE).titleColor(getResources().getColor(R.color.black))
                .progressIndeterminateStyle(horizontal)
                .cancelable(false)
                .show();
    }

    void loadDatabase() {
        loadQuotes();
        loadCategory();
        loadJob();
    }

    public void callRateUsDialog() {

        runnable = new Runnable() {
            @Override
            public void run() {
                // Set custom criteria (optional)
                RateThisApp.init(new RateThisApp.Config(3, 5));

                // Set callback (optional)
                RateThisApp.setCallback(new RateThisApp.Callback() {
                    @Override
                    public void onYesClicked() {
                    }

                    @Override
                    public void onNoClicked() {
                    }

                    @Override
                    public void onCancelClicked() {
                        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putBoolean("RemindLater", true);
                        edit.commit();
                    }
                });
                RateThisApp.showRateDialog(MainActivity.this);
            }
        };
        handler.postDelayed(runnable,1000);
    }

    void loadQuotes() {

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

                        List<Position> listPo = loginResponse.getPosition();

                      /*  SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor edit = pref.edit();
                        edit.commit();*/
                    }
                });
    }

    void loadJob() {

        ApiInterface apiInterface;
        apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        sb.append("salary");
        sb.append('"');
        Observable<JobScreenModel> quotes = apiInterface.getJobData();
        quotes
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JobScreenModel>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JobScreenModel loginResponse) {
                        addJobItems(loginResponse.getJob12());

                        System.out.println("JobData Size" + loginResponse);
                    }
                });
    }

    private void addJobItems(List<Job12> listCategoryName) {

        Job12 job = new Job12();
        int k = 0;
        for (int i = 0; i < listCategoryName.size(); i++) {

            job.setJobId(listCategoryName.get(i).getJobId());
            job.setJobName(listCategoryName.get(i).getJobName());
            job.setLastDate(listCategoryName.get(i).getLastDate());
            job.setPlace(listCategoryName.get(i).getPlace());
            job.setQualification(listCategoryName.get(i).getQualification());
            job.setSalary(listCategoryName.get(i).getSalary());
            k = ijobrepo.create(job);
        }

        if (k > -1) {
            Log.d("ORMLiteDemo", "WishItem item created");
        } else {
            System.out.println("Job Not Inserted");
        }
    }

    int i;
    void loadQuestion(final List<Categoryname> categoryName) {

        ApiInterface apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);

        for ( i = 0; i < categoryName.size()-1; i++) {

            final String categoryN = categoryName.get(i).getCatname();

            Observable<FetchQuestions> quotes = apiInterface.getQuestions(categoryName.get(i).getCatname());
            quotes
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<FetchQuestions>() {

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {

                               // if(categoryN.equals(categoryName.get(categoryName.size()-1))){
                                //}
                        }

                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(FetchQuestions loginResponse) {

                            List<Science> listQuestions  = loginResponse.getQuestionJson().get(0).getScience();
                            addQuestionsItems(listQuestions, categoryN);
                        }
                    });
        }

        final String categoryN = categoryName.get(categoryName.size()-1).getCatname();
        Observable<FetchQuestions> quotes = apiInterface.getQuestions(categoryName.get(categoryName.size()-1).getCatname());
        quotes
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FetchQuestions>() {

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                        // if(categoryN.equals(categoryName.get(categoryName.size()-1))){
                        m.dismiss();
                        //}
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FetchQuestions loginResponse) {

                        List<Science> listQuestions  = loginResponse.getQuestionJson().get(0).getScience();
                        addQuestionsItems(listQuestions, categoryN);
                    }
                });
    }

    List<Categoryname> list;

    void loadCategory() {

        List<String> categoryItem = new ArrayList<String>();

        ApiInterface apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);
        Observable<QuestionsCategories> quotes = apiInterface.getQuestionsCategory();
        quotes
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QuestionsCategories>() {
                    @Override

                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        loadQuestion(list);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(QuestionsCategories loginResponse) {
                        list = loginResponse.getCategoryname();
                        addCategoryItems(list);
                    }
                });
    }

    @OnClick(R.id.rl_ques)
    public void OnQueueClicked(View view) {
            startingActivity(Question_Category.class, false);

    }

    @OnClick(R.id.rl_info)
    public void OnSettingsClicked(View view) {
        startingActivity(SettingsActivity.class, false);
    }

    @OnClick(R.id.rl_job)
    public void OnJobClicked(View view) {

        if (Constants.getInstance().isNetworkAvailable(this)) {
            startingActivity(JobScreen.class, false);
        } else {
            Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), this);
        }
    }

   // public static int adNo;

    @Override
    protected void onResume() {
        super.onResume();

        Constants.btnAdMainActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseMainActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.mainActivity = MainActivity.this ;

        Constants.btnCloseMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnCloseMainActivity.setVisibility(View.GONE);
                Constants.btnAdMainActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnCloseMainActivity.setVisibility(View.GONE);
                Constants.btnAdMainActivity.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://mg-u.in"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.iv_rate)
    public void OnRateClicked(View view) {

        String url = "https://play.google.com/store/apps/details?id=com.rvappstudios.flashlight&hl=en";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

        if (Constants.getInstance().isNetworkAvailable(this)) {
            startingActivity(JobScreen.class, false);
        } else {
            Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), this);
        }
    }

    @OnClick(R.id.iv_info)
    public void OnInfoClicked(View view) {
        startingActivity(InfoScreen.class, false);
    }

    private void initDB() {
        DatabaseManager.init(this);
        irepo = new CategoryItemRepo(this);
        listrep = new WishScienceRepo(this);
        itemrep = new ScienceRepo(this);
        ijobrepo = new JobItemRepo(this);
    }

    private void addCategoryItems(List<Categoryname> listCategoryName) {

        Categoryname category = new Categoryname();
        int k = 0;

        for (int i = 0; i < listCategoryName.size(); i++) {

            category.setCatId(listCategoryName.get(i).getCatId());
            category.setCatname(listCategoryName.get(i).getCatname());
            k = irepo.create(category);
        }
    }

    private void addQuestionsItems(List<Science> listQuestions, String category) {

        WishScience list = new WishScience();
        list.setName(category);
        list.setId(1);
        list.setItems(null);

        int index = listrep.create(list);

        if (index > -1) {

            List<WishScience> lists = (List<WishScience>) listrep.findAll();

            WishScience list1 = lists.get(0);

            Science item = new Science();

            int k = 0;

            for (int i = 0; i < listQuestions.size(); i++) {

                item.setQuestionId(listQuestions.get(i).getQuestionId());
                item.setQuestion(listQuestions.get(i).getQuestion());
                item.setCategory(category);
                item.setList(list1);
                item.setQuestion(listQuestions.get(i).getQuestion());
                item.setAnswer(listQuestions.get(i).getAnswer());
                item.setAnswerF(listQuestions.get(i).getAnswerF());
                item.setOption1(listQuestions.get(i).getOption1());
                item.setOption2(listQuestions.get(i).getOption2());
                item.setOption3(listQuestions.get(i).getOption3());
                itemrep.create(item);
            }
        }

            int levelCount = 0;
            if (listQuestions != null) {
                levelCount = listQuestions.size() / 20;
                if (listQuestions.size() % 20 != 0) {
                    levelCount = levelCount + 1;
                }
            }
            for (int i = 1; i <= levelCount; i++) {
                UserScore userScore = new UserScore();
                userScore.setLevel_number(i);
                userScore.setCategory(category);
                userScore.setScore(-1);

                DatabaseHandler dbHandler = new DatabaseHandler(this);
                dbHandler.addLevelScore(userScore);
            }
    }

    JobItemRepo ijobrepo = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(callbackManager!= null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    Dialog dialogFacebook;

    private void showPictureialog() {

        dialogFacebook = new Dialog(this,
                android.R.style.Theme_Translucent);

        // Setting dialogview
        Window window = dialogFacebook.getWindow();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.gravity =Gravity.RIGHT   ;
        window.setAttributes(params);

        dialogFacebook.setTitle(null);
        dialogFacebook.setContentView(R.layout.sort);
        dialogFacebook.setCancelable(true);

        AppCompatTextView txtCancel = (AppCompatTextView) dialogFacebook.findViewById(R.id.iv_cancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFacebook.dismiss();
            }
        });

        RelativeLayout relativefacebook = (RelativeLayout)
                dialogFacebook.findViewById(R.id.facebook);
        relativefacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLogin();
            }
        });

        RelativeLayout relative_main = (RelativeLayout)
                dialogFacebook.findViewById(R.id.relative_main);
//        relative_main.setAlpha(0.2f);

        dialogFacebook.show();
    }

    @OnClick(R.id.iv_share)
    public void OnShareClicked(View view) {

        showPictureialog();

//        if (Constants.getInstance().isNetworkAvailable(this)) {
//            startingActivity(JobScreen.class, false);
//        } else {
//            Constants.getInstance().showAlert(getResources().getString(R.string.nointernet), this);
//        }
    }

    void facebookShare() {

       /* Bundle localBundle = new Bundle();
        localBundle.putString("link", "w");
        localBundle.putString("name", "0sd");
        localBundle.putString("caption", "sdww");
        localBundle.putString("picture", "fghthhth");
        localBundle.putString("description", "dftrrfgfrf");*/

        ShareDialog shareDialog = new ShareDialog(this);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle("Android")
                .setImageUrl(Uri.parse(""))
                .setContentDescription(
                        "Dummy Post For Testing")
                .setContentUrl(Uri.parse("https://www.al.in"))
                .build();
        shareDialog.show(linkContent);
    }

    public void facebookLogin(){

       /* if(isLoggedIn()) {
            facebookShare();
            if(dialogFacebook!= null) {
                dialogFacebook.dismiss();
            }
            Toast.makeText(getBaseContext(),"Direct Share", Toast.LENGTH_LONG).show();
        } else {

            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast.makeText(getBaseContext(),"Share Aftre Login", Toast.LENGTH_LONG).show();
                    if(dialogFacebook!= null) {
                        dialogFacebook.dismiss();
                    }
                    facebookShare();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getBaseContext(),"Facebook Login Error", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getBaseContext(),"Facebook Login Error", Toast.LENGTH_LONG).show();
                }
            });
        }*/
    }


    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private Toast toast;
    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.btnAdMainActivity = null;
        Constants.btnCloseMainActivity  =  null;
        Constants.mainActivity = null;
    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        Bitmap bitmap = null;

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
       /*
            appCompatImageViewAd = (ImageView) findViewById(R.id.appCompatImageViewAd);
            appCompatImageViewAd.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
            RelativeLayout.LayoutParams rParam = (RelativeLayout.LayoutParams) appCompatImageViewAd.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            rParam.width = (364 * height) / 83;
            System.out.println("Width Org" + width);
            System.out.println(
           // rParam.width = 50;
            rParam.height = height;
            appCompatImageViewAd.setLayoutParams(rParam);
*/
           // appCompatImageViewAd.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
           // appCompatImageViewAd.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));

                 /*   if (Build.VERSION.SDK_INT >= 23) {
                        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {


                           // storeImage(bitmap);

                        } else {

                            Log.v("TAG","Permission is revoked");

                            Toast.makeText(getApplicationContext(),"2" + bitmap, Toast.LENGTH_LONG).show();

                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    }
                    else { //permission is automatically granted on sdk<23 upon installation

                        Toast.makeText(getApplicationContext(),"3" + bitmap, Toast.LENGTH_LONG).show();

                        Log.v("TAG","Permission is granted");

                     //   storeImage(bitmap);
                    }*/
        }
    }
}

