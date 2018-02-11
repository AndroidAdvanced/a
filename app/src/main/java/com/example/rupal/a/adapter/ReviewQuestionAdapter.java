package com.example.rupal.a.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rupal.a.R;
import com.example.rupal.a.activities.QuestionsActivity;
import com.example.rupal.a.data.ReviewQuestion;

import java.util.List;

public class ReviewQuestionAdapter extends RecyclerView.Adapter<ReviewQuestionAdapter.MyViewHolder> {

    private List<ReviewQuestion> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtQuestion, txtAnswer, txtWrongAnswer;
        public ImageView imageRight;

        public MyViewHolder(View view) {
            super(view);
            txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
            txtAnswer = (TextView) view.findViewById(R.id.txtAnswer);
            imageRight = (ImageView) view.findViewById(R.id.imageRight);
            txtWrongAnswer = (TextView) view.findViewById(R.id.txtWrongAnswer);
        }
    }

    Drawable imgRight, imgWrong;
    public ReviewQuestionAdapter(List<ReviewQuestion> moviesList, Context mContext) {
        this.moviesList = moviesList;
        imgRight = mContext.getResources().getDrawable(R.drawable.right);
        imgWrong = mContext.getResources().getDrawable(R.drawable.wrong);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ReviewQuestion movie = moviesList.get(position);
        holder.txtQuestion.setText((position+1) + ".  "+movie.getQuestion());
        holder.txtAnswer.setText(movie.getAnswer());

           if(QuestionsActivity.listAnswer.get(position+1) == 1) {
                holder.imageRight.setBackgroundDrawable(imgRight);
                holder.txtWrongAnswer.setVisibility(View.GONE);
            } else if(QuestionsActivity.listAnswer.get(position+1) == 0){
                holder.imageRight.setBackgroundDrawable(imgWrong);
                holder.txtWrongAnswer.setText("Your Answer : "+ QuestionsActivity.listWrongAnswer.get(position+1));
                holder.txtWrongAnswer.setVisibility(View.VISIBLE);
            } else {
                try{
                    holder.imageRight.setBackgroundDrawable(null);
                    holder.txtWrongAnswer.setVisibility(View.GONE);
                } catch (Exception e){

                }
            }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
