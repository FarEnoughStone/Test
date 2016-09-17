package com.kui.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Administrator on 2016/9/14.
 */
public class NPCCircle extends View {

    private int width, height, time, TIME=50;
    private double  angle;
    private float circleX, circleY,circleR;
    private Paint paint, markPaint;
    private CanvasOfCircle circles;
    static boolean beEat = false;

    public NPCCircle(Context context, int w, int h,CanvasOfCircle c) {
        super(context);
        width = w;
        height = h;
        circles = c;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setColor((int) (Math.random() * 0xffffff) + 0xcc000000);
        markPaint = new Paint();
        markPaint.setColor(0x99000000 + 0x87CEEB);
        markPaint.setAntiAlias(true);
        markPaint.setStrokeWidth(3);
        markPaint.setStyle(Paint.Style.STROKE);
        circleR = 50;
        angle = Math.random() * Math.PI;
        circleX = (float) (Math.random() * width + 1);
        circleY = (float)(Math.random() * height + 1);
        new MyThread().start();
    }
    void addCircleR(float r) {
        circleR = (float) Math.sqrt(circleR * circleR + r * r);
    }

    public float getCircleR() {
        return circleR;
    }

    public float getCircleX() {
        return circleX;
    }

    public float getCircleY() {
        return circleY;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(circleX,circleY,circleR,paint);
        canvas.drawCircle(circleX,circleY,circleR/2,markPaint);
        float dx = 100 / circleR;
        if(time--<0) {
            angle = (Math.random()*5 + angle - Math.random()*5)%360 ;
            time = TIME;
        }
        circleX += (float) Math.cos(angle) * dx;
        circleY += (float) Math.sin(angle) * dx;
        if (circleX < 0) {
            time = 0;
            circleX = -circleX;
        } else if (circleX > width) {
            time = 0;
            circleX = 2 * width - circleX;
        }
        if (circleY < 0) {
            time = 0;
            circleY = -circleY;
        } else if (circleY > height) {
            time = 0;
            circleY = 2 * height - circleY;
        }
        if(beEat){
            circleX = (float) (Math.random() * width + 1);
            circleY = (float)(Math.random() * height + 1);
            circleR = 50;
            beEat = false;
        }
        invalidate();
    }

    class MyThread extends Thread {
        public void run() {
            while (true) {
                float x0 = circleX - circleR;
                float x1 = circleX + circleR;
                float y0 = circleY - circleR;
                float y1 = circleY + circleR;
                for(int i=0;i<circles.number;i++) {
                    if(x0 < circles.getCircleX(i) && x1 > circles.getCircleX(i) &&
                            y0 < circles.getCircleY(i) && y1 > circles.getCircleY(i)){
                        if (!beEat) {
                            addCircleR(circles.getCircleR(i));
                        }
                        circles.initCircle(i);
                    }
                }
            }
        }
    }
}
