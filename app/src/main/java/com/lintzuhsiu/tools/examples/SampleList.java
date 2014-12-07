package com.lintzuhsiu.tools.examples;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lintzuhsiu.tools.examples.listview.ListViewTester;
import com.lintzuhsiu.tools.examples.rounderview.TestRounderViewActivity;
import com.lintzuhsiu.tools.examples.viewpager.ViewPagerSlidingStripTester;

/**
 * Created by lintzuhsiu on 2014/7/15.
 */
public class SampleList extends ListActivity {

    private final static String[] practiceList = new String[]{
            "ViewPager's Tab Sliding Strip",
            "ListView drop down header",
            "RounderView"
    };

    private ListAdapter sampleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sampleListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, practiceList);
        this.setListAdapter(sampleListAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, ViewPagerSlidingStripTester.class);
                break;
            case 1:
                intent = new Intent(this, ListViewTester.class);
                break;
            case 2:
                intent = new Intent(this, TestRounderViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
