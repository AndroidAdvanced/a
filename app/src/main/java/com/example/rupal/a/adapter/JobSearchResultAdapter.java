package com.example.rupal.a.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.rupal.a.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.rupal.a.model.Job12;
import com.example.rupal.a.utils.Constants;

public class JobSearchResultAdapter extends RecyclerView.Adapter<JobSearchResultAdapter.ViewHolder> {

    private List<Job12> listData = new ArrayList<>();

    public JobSearchResultAdapter(List<Job12> list) {
        listData = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_job_screen, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt_job_title.setText(listData.get(position).getJobName());
        holder.txt_job_title.setTypeface(Constants.getInstance().fontMedium);
        holder.txt_salary.setText(listData.get(position).getSalary());
        holder.txt_salary.setTypeface(Constants.getInstance().fontRegular);
        holder.txt_qualification.setText(listData.get(position).getQualification());
        holder.txt_qualification.setTypeface(Constants.getInstance().fontRegular);
        holder.txt_place.setText(listData.get(position).getPlace());
        holder.txt_place.setTypeface(Constants.getInstance().fontRegular);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_job_title)
        AppCompatTextView txt_job_title;

        @BindView(R.id.txt_salary)
        AppCompatTextView txt_salary;

        @BindView(R.id.txt_qualification)
        AppCompatTextView txt_qualification;

        @BindView(R.id.txt_place)
        AppCompatTextView txt_place;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setData(List<Job12> list) {
        listData = list;
    }
}
