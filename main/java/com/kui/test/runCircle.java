package com.kui.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/4.
 */
public class runCircle extends View {

    private int circleR;
    private int w, h, time = 100;
    private double  oldAngle;
    private float circleX, circleY;
    private Paint paint;

    public runCircle(final Context context, final int width, final int height) {
        super(context);
        w = width;
        h = height;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setColor((int) (Math.random() * 0xffffff) + 0xcc000000);
        circleR = 100;
        oldAngle = (Math.random() * 360 + 1);
        circleX = (float) (Math.random() * width + 1);
        circleY = (float)(Math.random() * height + 1);
    }
    public void addCircleR(int r) {
        circleR += r;
    }

    public int getCircleR() {
        return circleR;
    }

    public int getCircleX() {
        return (int)circleX;
    }

    public int getCircleY() {
        return (int)circleY;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(circleX,circleY,circleR,paint);
        if(time--<0) {
            oldAngle = (Math.random()*5 + oldAngle - Math.random()*5)%360 ;
            time = 100;
        }
        circleX += (float) Math.cos(oldAngle) * 1;
        circleY += (float) Math.sin(oldAngle) * 1;
        if (circleX < 0) {
            circleX = -circleX;
        } else if (circleX > w) {
            circleX = 2 * w - circleX;
        }
        if (circleY < 0) {
            circleY = -circleY;
        } else if (circleY > h) {
            circleY = 2 * h - circleY;
        }
        invalidate();
    }
}
