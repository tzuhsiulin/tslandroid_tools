package com.lintzuhsiu.tools.examples.listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lintzuhsiu.tools.R;

/**
 * Created by lintzuhsiu on 2014/7/15.
 */
public class ListViewTester extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_tslist_view);

        ListView listView = (ListView) findViewById(R.id.sample_listview);
        listView.setAdapter(new ArrayAdapter(
            this, android.R.layout.simple_list_item_1, new String[]{"123", "123", "123", "123",
                "123", "123", "123", "123", "123", "123", "123", "123", "123", "123", "123",
                "123", "123", "123", "123", "123", "123", "123", "123", "123", "123", "123"}
        ));
    }

}
