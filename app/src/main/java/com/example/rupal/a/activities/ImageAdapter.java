package com.example.rupal.a.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.rupal.a.R;
import com.example.rupal.a.model.Science;
import com.example.rupal.a.utils.Constants;
import com.example.rupal.a.utils.UserScore;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    int levelCount = 0;
    private LayoutInflater mInflater;
    String category;
    Activity mActivity;
    ArrayList<UserScore> listUserScore;

    // Constructor
    public ImageAdapter(Context c, Activity activity, int levelCount, List<Science> listQuestions, String category, ArrayList<UserScore> listUserScore) {
        mContext = c;
        this.mActivity = activity;
        this.levelCount = levelCount;

        if(listQuestions.size() < 21) {
            Constants.listQuestions = listQuestions.subList(0,listQuestions.size());
        } else {
            Constants.listQuestions = listQuestions.subList(0,21);
        }

        this.listUserScore = listUserScore;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.category = category;
    }

    public int getCount() {
        return levelCount;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if( convertView == null ){
            //We must create a View:
            convertView = mInflater.inflate(R.layout.level_item, parent, false);

            holder = new ViewHolder();

            holder.txt_level_number = (AppCompatTextView) convertView.findViewById(R.id.txt_level_number);
            holder.relative_level = (PercentRelativeLayout) convertView.findViewById(R.id.relative_level);
            holder.txt_level_percentage = (AppCompatTextView) convertView.findViewById(R.id.txt_level_percentage);
            holder.btn_lock = (AppCompatImageView) convertView.findViewById(R.id.btn_lock);

            //setting up the Views
            convertView.setTag(holder);
        } else{
             holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_level_number.setText((position+1)+"");

        if(listUserScore.get(position).getScore() == -1) {
            holder.txt_level_percentage.setVisibility(View.INVISIBLE);
            holder.btn_lock.setVisibility(View.VISIBLE);
        } else {
            holder.txt_level_percentage.setVisibility(View.VISIBLE);
            holder.btn_lock.setVisibility(View.INVISIBLE);
            holder.txt_level_percentage.setText(""+listUserScore.get(position).getScore() + "%");
        }

        holder.relative_level.setOnClickListener(new OnClick(position));

        //Here we can do changes to the convertView, such as set a text on a TextView
        //or an image on an ImageView.
        return convertView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.center_line, R.drawable.center_line,
            R.drawable.center_line, R.drawable.center_line,
            R.drawable.center_line, R.drawable.center_line,
            R.drawable.center_line, R.drawable.center_line,
            R.drawable.center_line, R.drawable.center_line,
    };

    class ViewHolder {
        AppCompatTextView txt_level_number, txt_level_percentage;
        PercentRelativeLayout relative_level;
        AppCompatImageView btn_lock;
    }

    class OnClick implements View.OnClickListener {

        int position;
        OnClick(int position){
            this.position = position;
        }

        @Override
        public void onClick(View view) {

            List<Integer> listInt = new ArrayList<Integer>();

            int add = 0;
            for(int i = 0 ; i < levelCount; i ++) {
                listInt.add(add);
                add = add+20;
            }

            if(position == 0) {
                Intent intent = new Intent(mContext, QuestionsActivity.class);
                intent.putExtra(Constants.CATEGORY, category);
                intent.putExtra("level_number", (position + 1));
                intent.putExtra("start_index", listInt.get(position));
                mActivity.startActivity(intent);

            } else {

                if(listUserScore.get(position-1).getScore() != -1 && listUserScore.get(position).getScore() == -1) {

                    if (Constants.getInstance().isNetworkAvailable(mContext)) {

                        if( listUserScore.get(position-1).getScore() < 80 || listUserScore.get(position-1).getScore() == -1 ) {
                            Toast.makeText(mContext,"Please Pass Pre Exam", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(mContext, QuestionsActivity.class);
                            intent.putExtra(Constants.CATEGORY, category);
                            intent.putExtra("level_number", (position + 1));
                            intent.putExtra("start_index", listInt.get(position));
                            mActivity.startActivity(intent);
                        }
                    } else{
                        Constants.getInstance().showAlert(mContext.getResources().getString(R.string.nointernet),  mContext);
                    }
                } else {
                    if( listUserScore.get(position-1).getScore() < 80 || listUserScore.get(position-1).getScore() == -1 ) {
                        Toast.makeText(mContext,"Please Pass Pre Exam", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(mContext, QuestionsActivity.class);
                        intent.putExtra(Constants.CATEGORY, category);
                        intent.putExtra("level_number", (position + 1));
                        intent.putExtra("start_index", listInt.get(position));
                        mActivity.startActivity(intent);
                    }
                }
            }
        }
    }
}
