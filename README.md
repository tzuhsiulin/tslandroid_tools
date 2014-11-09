tslandroid_tools
================

1. PagerSlidingStrip
	```
	pagerSlidingStrip = (PagerSlidingStrip) findViewById(R.id.pager_sliding_strip);
	pager = (ViewPager) findViewById(R.id.test_viewpager);
	
	pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
	pager.setAdapter(pagerAdapter);
	
	pagerSlidingStrip.setViewPager(pager);
	```

2. TSListView
	```
	<com.tsl.android_practice.ui.listview.TSListView
        android:id="@+id/sample_listview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
	```
	
3. RounderView
	```
	<com.tsl.android_practice.ui.RounderView
        android:id="@+id/rounder_progress_bar"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="50dp"
        style="@style/DefaultRounderViewStyle">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="123"
            android:textSize="12sp"
            android:textColor="#fff" />

    </com.tsl.android_practice.ui.RounderView>
	```
	
4. RounderProgressBar

	xml:
	```
	<com.tsl.android_practice.ui.RounderProgressBar
        android:id="@+id/rounder_progress_bar"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="50dp"
        style="@style/DefaultRounderViewStyle">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="123"
            android:textSize="12sp"
            android:textColor="#fff" />

    </com.tsl.android_practice.ui.RounderProgressBar>
	```
	Java:	
	```
		RounderProgressBar progressBar = (RounderProgressBar) findViewById(R.id.rounder_progress_bar);
        progressBar.setProgress(Color.DKGRAY, 0.2f);
        progressBar.setProgress(Color.BLUE, 0.2f);
        progressBar.setProgress(Color.RED, 0.2f);
	```
