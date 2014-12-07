package com.lintzuhsiu.tools.ui.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lintzuhsiu.tools.R;
import com.lintzuhsiu.tools.ui.listview.impl.OnLoadListener;
import com.lintzuhsiu.tools.ui.listview.impl.OnRefreshListener;

public class TSListView extends ListView {

    private static class State {
        public static final int NORMAL = 0;
        public static final int PULLING = 1;
        public static final int PULLING_DOWN = 2;
        public static final int UPDATE = 3;
        public static final int LOAD_MORE = 4;
    }

    private Context context;
    private float startPoint;
    private int headerViewHeight;
    private int currentHeaderViewStatus;
    private View headerViewContainer;
    private LinearLayout headerView;
    private ImageView headerArrow;
    private TextView headerText;
    private RotateAnimation rotationAnimation;
    private OnLoadListener loadListener = new OnLoadListener() {
        @Override
        public void listLoadData() {

        }
    };
    private OnRefreshListener refreshListener = new OnRefreshListener() {
        @Override
        public void listUpdate() {

        }
    };
    private OnScrollListener scrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                setHeaderStatus(State.LOAD_MORE);
            }
        }
    };

    public TSListView(Context context) {
        this(context, null);
    }

    public TSListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TSListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        headerViewHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, dm);
        this.context = context;

        rotationAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotationAnimation.setDuration(250);
        rotationAnimation.setFillEnabled(true);
        rotationAnimation.setFillAfter(true);

        initHeader();
        this.setOnScrollListener(scrollListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPoint = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentPoint = event.getY();
                float offsetDistance = currentPoint - startPoint;
                int headerViewTopMargin = (int) (offsetDistance - headerViewHeight);

                setHeader(headerViewTopMargin);
                if (currentHeaderViewStatus != State.PULLING_DOWN) {
                    setHeaderStatus(State.PULLING);
                }

                if (currentHeaderViewStatus != State.PULLING_DOWN && (headerViewTopMargin * 2) > headerViewHeight) {
                    setHeaderStatus(State.PULLING_DOWN);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentHeaderViewStatus == State.PULLING_DOWN) {
                    setHeaderStatus(State.UPDATE);
                }
                else {
                    setHeaderStatus(State.NORMAL);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOnUpdateListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void setOnLoadMoreListener(OnLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public void refreshDone() {
        setHeaderStatus(State.NORMAL);
    }

    private void initHeader() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerViewContainer = inflater.inflate(R.layout.listview_header, null);
        headerView = (LinearLayout) headerViewContainer.findViewById(R.id.header_refresh);
        headerArrow = (ImageView) headerViewContainer.findViewById(R.id.header_arrow);
        headerText = (TextView) headerViewContainer.findViewById(R.id.header_hint_text);
        this.addHeaderView(headerViewContainer, null, false);
    }

    private void setHeader(int headerViewTopMargin) {
        LinearLayout.LayoutParams headerViewLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, headerViewHeight);
        headerViewLayoutParams.setMargins(0, headerViewTopMargin, 0, 0);
        headerView.setLayoutParams(headerViewLayoutParams);
    }

    private void setHeaderStatus(int status) {
        currentHeaderViewStatus = status;

        switch (status) {
            case State.NORMAL:
                setHeader(-headerViewHeight);
                break;
            case State.PULLING:
                headerText.setText(getResources().getString(R.string.listview_header_pulling));
                headerArrow.setVisibility(View.VISIBLE);
                break;
            case State.PULLING_DOWN:
                headerText.setText(getResources().getString(R.string.listview_header_pulling_down));
                headerArrow.setVisibility(View.VISIBLE);
                headerArrow.clearAnimation();
                headerArrow.startAnimation(rotationAnimation);
                break;
            case State.UPDATE:
                headerText.setText(getResources().getString(R.string.listview_header_update));
                headerArrow.setVisibility(View.GONE);
                setHeader(0);
                refreshListener.listUpdate();
                break;
            case State.LOAD_MORE:
                loadListener.listLoadData();
                break;
        }
    }

}
