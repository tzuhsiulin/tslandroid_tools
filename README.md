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
	
3. CircleProgressBar

	xml:
	```
	<com.lintzuhsiu.tools.ui.RounderProgressBar
        android:id="@+id/circle_progress_bar"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        style="@style/DefaultCircleViewStyle">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="123"
            android:textSize="12sp"
            android:textColor="#fff" />

    </com.lintzuhsiu.tools.ui.RounderProgressBar>
	```
	Java:	
	```
	CircleProgressView progressBar = (CircleProgressView) findViewById(R.id.circle_progress_bar);
    progressBar.setProgress(0.2f);
    progressBar.setProgress(0.2f);
    progressBar.setProgress(0.2f);
	```


License
================
The MIT License (MIT)

Copyright (c) 2014 TzuhSiuLin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.