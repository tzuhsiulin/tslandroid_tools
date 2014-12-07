package com.lintzuhsiu.tools.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by lintzuhsiu on 14/11/9.
 */
public class RounderProgressBar extends RounderView {

    private int roundProgress;

    public RounderProgressBar(Context context) {
        this(context, null);
    }

    public RounderProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RounderProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawOuterCircle(canvas);
        drawProgress(canvas);
        drawInnerCircle(canvas);
    }

    public void setRoundProgress(int progress) {
        this.roundProgress = progress;
        invalidate();
    }

    private void drawProgress(Canvas canvas) {
        RectF rectf = new RectF();
        rectf.set(
                circleCenterPoint.x - radius,
                circleCenterPoint.y - radius,
                circleCenterPoint.x + radius,
                circleCenterPoint.y + radius
        );

        paint.setColor(rounderProgressBarColor);

        float sweepAngle = Math.round(roundProgress * 360);
        canvas.drawArc(rectf, -90, sweepAngle, true, paint);
    }

}
