package com.lintzuhsiu.tools.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lintzuhsiu on 14/12/7.
 */
public class AccountRounderProgressBar extends RounderProgressBar {

    private HashMap<Integer, Float> progressDetail;

    public AccountRounderProgressBar(Context context) {
        this(context, null);
    }

    public AccountRounderProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccountRounderProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        progressDetail = new HashMap<Integer, Float>();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPercentProgressBar(canvas);
    }

    public void setPercentProgress(int color, float progress) {
        progressDetail.put(color, progress);
        invalidate();
    }

    private void drawPercentProgressBar(Canvas canvas) {
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
            sweepAngle = Math.round(pairs.getValue() * 360);
            canvas.drawArc(rectf, startAngle1, sweepAngle, true, paint);
//            canvas.drawRect();
        }
    }

}
