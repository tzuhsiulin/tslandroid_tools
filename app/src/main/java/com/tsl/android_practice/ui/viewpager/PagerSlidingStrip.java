package com.tsl.android_practice.ui.viewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsl.android_practice.R;

/**
 * Created by lintzuhsiu on 2014/7/11.
 */
public class PagerSlidingStrip extends HorizontalScrollView {

    private final static int defaultTabTextSize = 12;
    private final static int defaultTabLRPadding = 15;
    private final static int defaultTabTBPadding = 8;
    private final static int defaultDivideLineWidth = 1;
    private final static int defaultDivisionLineHeight = 3;

    private int tabTextSize;
    private int tabLRPadding;
    private int tabTBPadding;
    private int divideLineWidth;
    private int divideLineColor;
    private int divisionLineHeight;
    private int divisionLineColor;
    private int indicatorLineColor;
    private int positionOffset;

    private int currentPagePosition = 0;
    private int indicatorLineStart;
    private int indicatorLineEnd;
    private Context context;
    private LinearLayout tabContainer;
    private ViewPager viewPager;
    private Paint dividePaint;
    private Paint divisionPaint;
    private Paint indicatorPaint;

    private ViewPager.OnPageChangeListener pageChangerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixel) {
            View tmpTab = tabContainer.getChildAt(position);

            int tabWidth = tmpTab.getRight() - tmpTab.getLeft();
            indicatorLineStart = tmpTab.getLeft() + (int)(tabWidth * positionOffset);
            indicatorLineEnd = tmpTab.getRight() + (int)(tabWidth * positionOffset);

            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            currentPagePosition = position;
            scrollTo(tabContainer.getChildAt(position).getLeft(), 0);
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private OnClickListener tabClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();

            currentPagePosition = position;
            viewPager.setCurrentItem(position);
        }
    };

    public PagerSlidingStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setWillNotDraw(false);
        setFillViewport(true);

        getCustomAttrs(attrs);
        initTabContainer();

        dividePaint = new Paint();
        dividePaint.setAntiAlias(true);
        dividePaint.setColor(divideLineColor);
        dividePaint.setStrokeWidth(divideLineWidth);

        divisionPaint = new Paint();
        divisionPaint.setAntiAlias(true);
        divisionPaint.setColor(divisionLineColor);

        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setColor(indicatorLineColor);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int tabCount = tabContainer.getChildCount();
        int tabContainerHeight = tabContainer.getHeight();
        int leftPosition, rightPosition;
        View tmpTab;

        if (tabCount == 0) {
            return;
        }

//      draw division line
        {
            canvas.drawRect(0, tabContainerHeight - divisionLineHeight, tabContainer.getWidth(), tabContainerHeight, divisionPaint);
        }

//      draw divide line
        {
            for (int i = 0; i < tabCount; i++) {
                tmpTab = tabContainer.getChildAt(i);
                leftPosition = tmpTab.getLeft();
                rightPosition = tmpTab.getRight();

                canvas.drawLine(leftPosition, tabTBPadding, leftPosition, tmpTab.getMeasuredHeight() - tabTBPadding, dividePaint);
                canvas.drawLine(rightPosition, tabTBPadding, rightPosition, tmpTab.getMeasuredHeight() - tabTBPadding, dividePaint);
            }
        }

//      draw indicator line
        {
            canvas.drawRect(indicatorLineStart, tabContainerHeight - tabTBPadding, indicatorLineEnd, tabContainerHeight, indicatorPaint);
        }
    }

    private void getCustomAttrs(AttributeSet attrs) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, defaultTabTextSize, dm);
        tabLRPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defaultTabLRPadding, dm);
        tabTBPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defaultTabTBPadding, dm);
        divideLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defaultDivideLineWidth, dm);
        divisionLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defaultDivisionLineHeight, dm);

        divideLineColor = context.getResources().getColor(R.color.tab_divide_line);
        divisionLineColor = context.getResources().getColor(R.color.tab_division_line);
        indicatorLineColor = context.getResources().getColor(R.color.tab_indicator_line);
    }

    private void initTabContainer() {
        tabContainer = new LinearLayout(context);
        tabContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        this.addView(tabContainer);
    }

    private void addTab(String tabText) {
        TextView tab = new TextView(context);
        tab.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tab.setPadding(tabLRPadding, tabTBPadding, tabLRPadding, tabTBPadding);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        tab.setTextSize(tabTextSize);
        tab.setText(tabText);
        tab.setBackgroundResource(R.drawable.tab_background);
        tab.setTag(tabContainer.getChildCount());
        tab.setOnClickListener(tabClickListener);

        tabContainer.addView(tab);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;

        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("viewpager doesn't have adapter instance.");
        }

        this.viewPager.setOnPageChangeListener(pageChangerListener);
        for (int i = 0; i < this.viewPager.getAdapter().getCount(); i++) {
            addTab(this.viewPager.getAdapter().getPageTitle(i).toString());
        }
    }

}
