package com.example.rupal.a.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.rupal.a.R;
import com.example.rupal.a.activities.QuestionsActivity;
import com.example.rupal.a.data.ApiInterface;
import com.example.rupal.a.model.Science;
import com.example.rupal.a.utils.Constants;

public class QuestionsAdapter extends PagerAdapter {

    @BindView(R.id.tv_questions)
    AppCompatTextView tvQuestions;
    @BindView(R.id.tv_answer_1)
    AppCompatTextView tvAnswer1;
    @BindView(R.id.tv_answer_2)
    AppCompatTextView tvAnswer2;
    @BindView(R.id.tv_answer_3)
    AppCompatTextView tvAnswer3;
    @BindView(R.id.tv_answer_4)
    AppCompatTextView tvAnswer4;
    @BindView(R.id.tv_answer_1_result)
    AppCompatImageView ivResult1;
    @BindView(R.id.tv_answer_2_result)
    AppCompatImageView ivResult2;
    @BindView(R.id.tv_answer_3_result)
    AppCompatImageView ivResult3;
    @BindView(R.id.tv_answer_4_result)
    AppCompatImageView ivResult4;
    @BindView(R.id.alert)
    AppCompatImageView alert;

    private List<AppCompatImageView> listResult= new ArrayList<>();

    private Context mContext;
    private List<Science> listQuestions;
    private int currentPosition;
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, View> listViews = new HashMap<>();
    private OnAnswerGivenListener onAnswerGivenListener;

    private HashMap<Integer, List<AppCompatImageView>> customImageListView = new HashMap<>();

    ProgressDialog progressDialog;

    String category;

    int textsize;

    public QuestionsAdapter(Context context, List<Science> list, ProgressDialog progressDialog, String category, int textsize) {
        mContext = context;
        listQuestions = list;
        this.progressDialog = progressDialog;
        this.category = category;
        if(textsize == 0){
            this.textsize = (int) mContext.getResources().getDimension(R.dimen.six_dp);
        } else if(textsize == 1) {
            this.textsize = (int) mContext.getResources().getDimension(R.dimen.seven_dp);
        } else if(textsize == 2) {
            this.textsize = (int) mContext.getResources().getDimension(R.dimen.eight_dp);
        } else if(textsize == 3) {
            this.textsize = (int) mContext.getResources().getDimension(R.dimen.nine_dp);
        } else if(textsize == 4) {
            this.textsize = (int) mContext.getResources().getDimension(R.dimen.ten_dp);
        } else if(textsize == 5) {
            this.textsize = (int) mContext.getResources().getDimension(R.dimen.eleven_dp);
        } else if(textsize == 6) {
            this.textsize = (int) mContext.getResources().getDimension(R.dimen.twelve_dp);
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.alert)
    void onAlertClicked(View view) {

        if (Constants.getInstance().isNetworkAvailable(mContext)) {
            new MaterialDialog.Builder(mContext)
                    .title("write your query about question here")
                    .backgroundColor(Color.WHITE).titleColor(mContext.getResources().getColor(R.color.material_red))
                    .contentColorRes(R.color.black)
                    .inputRangeRes(10, 10000, R.color.material_red)
                    .input(null, null, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            // Do something

                            if (Constants.getInstance().isNetworkAvailable(mContext)) {
                                ApiInterface apiInterface;
                                apiInterface = Constants.getInstance().getRetrofit().create(ApiInterface.class);
                                Observable<String> quotes = apiInterface.postReportQuestion(category, listQuestions.get(QuestionsActivity.currentQuestion-1).getQuestion(),input.toString());
            quotes
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(mContext,"Your Question Reported Successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String loginResponse) {
                            //Constants.getInstance().showLog(loginResponse);
                        }
                    });
                            } else {
                                Constants.getInstance().showAlert(mContext.getResources().getString(R.string.nointernet), mContext);
                            }
                        }
                    }).show();
        } else {
            Constants.getInstance().showAlert(mContext.getResources().getString(R.string.nointernet), mContext);
            return;
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tv_answer_1)
    void onAnswer1Clicked(View view) {
        checkAnswerCorrection(1, true, "1");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tv_answer_2)
    void onAnswer2Clicked(View view) {
        checkAnswerCorrection(2, true, "2");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tv_answer_3)
    void onAnswer3Clicked(View view)
    {
        checkAnswerCorrection(3, true ,"3");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tv_answer_4)
    void onAnswer4Clicked(View view) {
        checkAnswerCorrection(4, true, "4");
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.questions_fragment, collection, false);
        ButterKnife.bind(this, layout);
        collection.addView(layout);

        listViews.put(position, layout);
        tvQuestions.setText(listQuestions.get(position).getQuestion());
        tvQuestions.setTextSize(textsize);

        tvAnswer1.setText(listQuestions.get(position).getOption1());
        tvAnswer1.setTextSize(textsize);

        tvAnswer2.setText(listQuestions.get(position).getOption2());
        tvAnswer2.setTextSize(textsize);

        tvAnswer3.setText(listQuestions.get(position).getOption3());
        tvAnswer3.setTextSize(textsize);

        tvAnswer4.setText(listQuestions.get(position).getAnswerF());
        tvAnswer4.setTextSize(textsize);

        currentPosition = position;
        addingItemsToList(position);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
       collection.removeView((View) view);
        listViews.remove(position-1);
    }

    @Override
    public int getCount() {
        return listQuestions.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

   public void checkAnswerCorrection(int position, boolean moveToNext, String from ) {

       listResult = customImageListView.get(QuestionsActivity.currentQuestion-1);

       if (listQuestions.get(QuestionsActivity.currentQuestion-1).getAnswer().equals(listQuestions.get(QuestionsActivity.currentQuestion-1).getOption(position))) {
            listResult.get(position-1).setImageResource(R.drawable.right);

            if (onAnswerGivenListener != null) {
                onAnswerGivenListener.onAnswerGiven(QuestionsActivity.currentQuestion-1, position, moveToNext, true, listResult);
            }
           QuestionsActivity.listAnswer.put(QuestionsActivity.currentQuestion-1, 1); // Used For Review

        } else {

          listResult.get(position-1).setImageResource(R.drawable.wrong);
          QuestionsActivity.listAnswer.put(QuestionsActivity.currentQuestion-1, 0);// Used For Review
           QuestionsActivity.listWrongAnswer.put(QuestionsActivity.currentQuestion-1,listQuestions.get(QuestionsActivity.currentQuestion-1).getOption(position));

            int correctAnswerPosition = 0;
            for (int i = 0; i < 4; i++) {
                if (listQuestions.get(QuestionsActivity.currentQuestion-1).getAnswer().equals(listQuestions.get(QuestionsActivity.currentQuestion-1).getOption(i+1))) {
                    correctAnswerPosition = i;
                    break;
                }
            }

           listResult.get(correctAnswerPosition).setImageResource(R.drawable.right);
            listResult.get(correctAnswerPosition).setVisibility(View.VISIBLE);
            onAnswerGivenListener.onAnswerGiven(QuestionsActivity.currentQuestion-1, position, moveToNext, false, listResult);
        }
       listResult.get(position-1).setVisibility(View.VISIBLE);
    }

    public void addingItemsToList(int position) {

        listResult = new ArrayList<>();
        listResult.add(ivResult1);
        listResult.add(ivResult2);
        listResult.add(ivResult3);
        listResult.add(ivResult4);

        customImageListView.put(position, listResult);
    }

    public void setOnAnswerGivenListener(OnAnswerGivenListener onAnswerGivenListener) {
        this.onAnswerGivenListener = onAnswerGivenListener;
    }

    public interface OnAnswerGivenListener {
        void onAnswerGiven(int position, int selectedOption, boolean moveToNext, boolean answeredCorrectly, List<AppCompatImageView> listResult);
    }
}
