package com.example.rupal.a.utils;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

/**
 * Created by Rupal on 12/26/2017.
 */
// Schedule Job using Job Scheduler.
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class mJobScheduler  extends JobService{

   private mJobExecutor mJobExecutor;

    @Override
    public boolean onStartJob(final JobParameters params) {

        android.widget.Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show();

        System.out.println("Job Started RRRRRRRRRRRRr");

       mJobExecutor = new mJobExecutor() {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                jobFinished(params, false); // If you need reschedule job again, use true.
            }
        };
        mJobExecutor.execute();
        return true; // If you put background job on seperate thread, You have to return true
                     // If you return true from onStartJob, You need to call job finished.
    }

    @Override
    public boolean onStopJob(JobParameters params) { // If Job interrupted before finish, this method will be invoked.
        mJobExecutor.cancel(true);
        return false; // If want to reschedule same job again, return true.
    }
}
