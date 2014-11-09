package com.tsl.android_practice.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lintzuhsiu on 14/11/9.
 */
public class RounderProgressBar extends RounderView {

    private HashMap<Integer, Float> progressDetail;

    public RounderProgressBar(Context context) {
        super(context);
        progressDetail = new HashMap<Integer, Float>();
    }

    public RounderProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressDetail = new HashMap<Integer, Float>();
    }

    public RounderProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        progressDetail = new HashMap<Integer, Float>();
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawOuterCircle(canvas);
        drawProgress(canvas);
        drawInnerCircle(canvas);
    }

    public void setProgress(int color, float progress) {
        progressDetail.put(color, progress);
        invalidate();
    }

    private void drawProgress(Canvas canvas) {
        Iterator iterator = progressDetail.entrySet().iterator();
        for (int startAngle1 = -90, sweepAngle = 0; iterator.hasNext(); startAngle1 += sweepAngle) {
            Map.Entry<Integer, Float> pairs = (Map.Entry) iterator.next();

            RectF rectf = new RectF();
            rectf.set(
                    circleCenterPoint.x - radius,
                    circleCenterPoint.y - radius,
                    circleCenterPoint.x + radius,
                    circleCenterPoint.y + radius
            );

            paint.setColor(pairs.getKey());
            sweepAngle = (int) (pairs.getValue() * 360);
            canvas.drawArc(rectf, startAngle1, sweepAngle, true, paint);
        }
    }

}
