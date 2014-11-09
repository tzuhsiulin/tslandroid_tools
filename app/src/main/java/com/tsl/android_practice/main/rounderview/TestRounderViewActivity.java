package com.tsl.android_practice.main.rounderview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.tsl.android_practice.R;
import com.tsl.android_practice.ui.RounderProgressBar;

/**
 * Created by lintzuhsiu on 14/11/8.
 */
public class TestRounderViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_rounderview);

        RounderProgressBar progressBar = (RounderProgressBar) findViewById(R.id.rounder_progress_bar);
        progressBar.setProgress(Color.DKGRAY, 0.2f);
        progressBar.setProgress(Color.BLUE, 0.2f);
        progressBar.setProgress(Color.RED, 0.2f);
    }

}
