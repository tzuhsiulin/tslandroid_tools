package com.lintzuhsiu.tools.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lintzuhsiu.tools.R;

/**
 * Created by lintzuhsiu on 14/11/8.
 */
public class RounderView extends FrameLayout {

    class PaddingType {
        public static final int LEFT    = 0;
        public static final int TOP     = 1;
        public static final int RIGHT   = 2;
        public static final int BOTTOM  = 3;
    }

    private Context context;
    private int viewHeight;
    private int viewWidth;
    private int diameter;
    protected int radius;
    private float thickness;
    protected Point circleCenterPoint;

    // style attrs
    private int backgroundColor;
    private float outerCircleSize;
    private float innerCircleSize;
    private int innerCircleColor;
    private int outerCircleColor;
    protected int rounderProgressBarColor;
    protected float[] paddingValue;

    // draw attr
    protected Paint paint;
    private RectF outerRect;
    private RectF innerRect;

    public RounderView(Context context) {
        this(context, null);
    }

    public RounderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RounderView, 0, 0);
//        try {
//            outerCircleSize = typedArray.getDimension(R.styleable.RounderView_outer_circle_size,
//                    getResources().getDimension(R.dimen.default_outer_cycle_size));
//            innerCircleSize = typedArray.getDimension(R.styleable.RounderView_inner_circle_size,
//                    getResources().getDimension(R.dimen.default_inner_cycle_size));
//            outerCircleColor = typedArray.getColor(R.styleable.RounderView_outer_circle_color,
//                    getResources().getColor(R.color.rounderview_outer_circle));
//            innerCircleColor = typedArray.getColor(R.styleable.RounderView_inner_circle_color,
//                    getResources().getColor(R.color.rounderview_inner_circle));
//        } finally {
//            typedArray.recycle();
//        }
    }

    public RounderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        paddingValue = new float[4];

        outerRect = new RectF();
        innerRect = new RectF();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RounderView,
                defStyle, R.style.DefaultRounderViewStyle);
        try {
            outerCircleSize = typedArray.getDimension(R.styleable.RounderView_outer_circle_size, 0);
            innerCircleSize = typedArray.getDimension(R.styleable.RounderView_inner_circle_size, 0);
            outerCircleColor = typedArray.getColor(R.styleable.RounderView_outer_circle_color, 0);
            innerCircleColor = typedArray.getColor(R.styleable.RounderView_inner_circle_color, 0);
            rounderProgressBarColor = typedArray.getColor(R.styleable.RounderView_rounder_progressbar_color, 0);
        } finally {
            typedArray.recycle();
        }

        thickness = outerCircleSize - innerCircleSize;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // match_parent = EXACTLY
        // wrap_content = AT_MOST
        // undefined = UNSPECIFIED
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);

        // get parent size
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        // getting screen size if viewHeight's size is 0
        if (viewHeight == 0) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            viewWidth = display.getWidth();
            viewHeight = display.getHeight();
        }

        // get margin value
        paddingValue[0] = getPaddingLeft();
        paddingValue[1] = getPaddingTop();
        paddingValue[2] = getPaddingRight();
        paddingValue[3] = getPaddingBottom();

        // cal circle diameter
        int measureSize = 0;
        if (viewWidth > viewHeight) {
            diameter = viewHeight - (int) (paddingValue[1] + paddingValue[3]);
            measureSize = diameter;
        }
        else {
            diameter = viewWidth - (int)(paddingValue[0] + paddingValue[2]);
            measureSize = viewWidth;
        }

        // cal radius
        ViewGroup parentView = (ViewGroup) getParent();
        radius = diameter / 2;
        circleCenterPoint = new Point(
                viewWidth / 2 + getParentPadding(0, PaddingType.LEFT),
                measureSize / 2 + getParentPadding(0, PaddingType.TOP));

        // set child view size
//        for (int i = 0; i < getChildCount(); i++) {
//            View itemView = getChildAt(i);
//            int a = itemView.getMeasuredWidth();
//
//            if (a >= diameter) {
//                measureChildWithMargins(itemView,
//                        MeasureSpec.makeMeasureSpec(diameter, MeasureSpec.UNSPECIFIED),
//                        (int) ((thickness * 2) - (paddingValue[0] + paddingValue[2])),
//                        MeasureSpec.makeMeasureSpec(diameter, MeasureSpec.UNSPECIFIED),
//                        (int) ((thickness * 2) - (paddingValue[0] + paddingValue[2]))
//                );
//            }
//
//        }

        // set view size
        setMeasuredDimension(viewWidth, measureSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        for (int i = 0; i < getChildCount(); i++) {
            View itemView = getChildAt(i);
            float itemWidth = itemView.getMeasuredWidth();
            float itemHeight = itemView.getMeasuredHeight();

            itemView.layout(
                    (int) (circleCenterPoint.x - (itemWidth / 2)),
                    (int) (circleCenterPoint.y - (itemHeight / 2)),
                    (int) (circleCenterPoint.x + (itemWidth / 2)),
                    (int) (circleCenterPoint.y + (itemHeight / 2))
            );
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.restore();
        drawOuterCircle(canvas);
//        drawInnerCircle(canvas);
    }

    @Override
    public void setBackgroundColor(int color) {
//        super.setBackgroundColor(color);
        outerCircleColor = color;
        invalidate();
    }

    protected void drawOuterCircle(Canvas canvas) {
        paint.setColor(outerCircleColor);

        outerRect.set(
                circleCenterPoint.x - radius,
                circleCenterPoint.y - radius,
                circleCenterPoint.x + radius,
                circleCenterPoint.y + radius
        );

        // oval, startAngle, sweepAngle, useCenter, paint
        canvas.drawArc(outerRect, 0, 360, true, paint);
    }

    protected void drawInnerCircle(Canvas canvas) {
        paint.setColor(innerCircleColor);

        innerRect.set(
                circleCenterPoint.x - radius + thickness,
                circleCenterPoint.y - radius + thickness,
                circleCenterPoint.x + radius - thickness,
                circleCenterPoint.y + radius - thickness
        );

        // oval, startAngle, sweepAngle, useCenter, paint
        canvas.drawArc(innerRect, 0, 360, true, paint);
    }

    private int getParentPadding(int paddingValue, int type) {
        ViewGroup view = (ViewGroup) getParent();

        if (view == null) {
            return paddingValue;
        }

        switch (type) {
            case PaddingType.LEFT:
                paddingValue += view.getPaddingLeft();
                break;
            case PaddingType.TOP:
                paddingValue += view.getPaddingTop();
                break;
            case PaddingType.RIGHT:
                paddingValue += view.getPaddingRight();
                break;
            case PaddingType.BOTTOM:
                paddingValue += view.getPaddingBottom();
                break;
        }
        return paddingValue;
    }

}
