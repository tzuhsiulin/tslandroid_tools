package com.tsl.android_practice.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.tsl.android_practice.R;

/**
 * Created by lintzuhsiu on 14/11/8.
 */
public class RounderView extends FrameLayout {

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
        paddingValue = new float[4];

        outerRect = new RectF();
        innerRect = new RectF();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)) ;


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RounderView,
                defStyle, R.style.DefaultRounderViewStyle);
        try {
            outerCircleSize = typedArray.getDimension(R.styleable.RounderView_outer_circle_size, 0);
            innerCircleSize = typedArray.getDimension(R.styleable.RounderView_inner_circle_size, 0);
            outerCircleColor = typedArray.getColor(R.styleable.RounderView_outer_circle_color, 0);
            innerCircleColor = typedArray.getColor(R.styleable.RounderView_inner_circle_color, 0);
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

        // get margin value
        paddingValue[0] = getPaddingLeft();
        paddingValue[1] = getPaddingTop();
        paddingValue[2] = getPaddingRight();
        paddingValue[3] = getPaddingBottom();

        // cal circle diameter
        int measureSize = 0;
        if (viewWidth > viewHeight && viewHeight != 0) {
            diameter = viewHeight - (int)(paddingValue[1] + paddingValue[3]);
            measureSize = diameter;
        }
        else {
            diameter = viewWidth - (int)(paddingValue[0] + paddingValue[2]);
            measureSize = viewWidth;
        }

        // cal radius
        radius = diameter / 2;
        circleCenterPoint = new Point(measureSize / 2, measureSize / 2);

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
        setMeasuredDimension(measureSize, measureSize);
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
                    (int) (circleCenterPoint.y + (itemWidth / 2))
            );
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOuterCircle(canvas);
        drawInnerCircle(canvas);
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

}
