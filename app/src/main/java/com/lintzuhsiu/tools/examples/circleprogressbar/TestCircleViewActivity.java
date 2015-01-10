package com.lintzuhsiu.tools.examples.circleprogressbar;

import android.app.Activity;
import android.os.Bundle;

import com.lintzuhsiu.tools.R;
import com.lintzuhsiu.tools.ui.CircleProgressView;

/**
 * Created by lintzuhsiu on 14/11/8.
 */
public class TestCircleViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_circleprogressbar);

        CircleProgressView progressBar = (CircleProgressView) findViewById(R.id.circle_progress_bar);
        progressBar.setProgress(0.2f);
        progressBar.setProgress(0.2f);
        progressBar.setProgress(0.2f);
    }

}
