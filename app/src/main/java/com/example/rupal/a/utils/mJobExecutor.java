package com.example.rupal.a.utils;

import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Rupal on 12/26/2017.
 */

public class mJobExecutor extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {

        System.out.println("RUPALTAG doInBackground");

        return "Background Long Running Task Finishes.....";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
