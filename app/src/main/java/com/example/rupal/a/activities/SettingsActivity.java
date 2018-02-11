package com.example.rupal.a.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.rupal.a.R;
import com.example.rupal.a.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rupal on 12/28/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);
        ButterKnife.bind(this);

        Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        iv_back.setImageDrawable(upArrow);

        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        Constants.btnAdSettingsActivity =  (Button) findViewById(R.id.appCompatImageViewAd);
        Constants.btnCloseSettingsActivity  =  (Button) findViewById(R.id.btnClose);
        Constants.settingsActivity = SettingsActivity.this;

        Constants.btnAdSettingsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdSettingsActivity.setVisibility(View.GONE);
                Constants.btnCloseSettingsActivity.setVisibility(View.GONE);
            }
        });

        Constants.btnAdSettingsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.btnAdSettingsActivity.setVisibility(View.GONE);
                Constants.btnCloseSettingsActivity.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://mg-u.in"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.btnAdSettingsActivity = null;
        Constants.btnCloseSettingsActivity  =  null;
        Constants.settingsActivity = null;
    }

    @OnClick(R.id.txt_setsize)
    public void onTextSizeClicked(View view) {
        new MaterialDialog.Builder(this)
                .title("Text Size")
                .items(R.array.textsize)
                .theme(Theme.DARK)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("textsize", which);
                                editor.commit();

                        return true;
                    }
                })
                .positiveText("SAVE")
                .negativeText("CANCEL")
                .show();
    }
}
