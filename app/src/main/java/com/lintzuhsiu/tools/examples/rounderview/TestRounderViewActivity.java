package com.lintzuhsiu.tools.examples.rounderview;

import android.app.Activity;
import android.os.Bundle;

import com.lintzuhsiu.tools.ui.RounderProgressBar;
import com.lintzuhsiu.tools.R;

/**
 * Created by lintzuhsiu on 14/11/8.
 */
public class TestRounderViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_rounderview);

        RounderProgressBar progressBar = (RounderProgressBar) findViewById(R.id.rounder_progress_bar);
//        progressBar.setProgress(Color.DKGRAY, 0.2f);
//        progressBar.setProgress(Color.BLUE, 0.2f);
//        progressBar.setProgress(Color.RED, 0.2f);
    }

}
