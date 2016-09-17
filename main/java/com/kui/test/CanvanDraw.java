package com.kui.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/9/12.
 */
public class CanvanDraw extends View {

    private MyCircle myCircle[];
    private int number = 10;
    private int width, height;
    private Paint paint;
    private int time = 100;
    private int dx = 2;

    public CanvanDraw(Context context,int w,int h) {
        super(context);
        width = w;
        height = h;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);//抗锯齿
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawCircle(canvas);
        circleChange();
        invalidate();
    }

    void setCircleR(int i, float r) {
        myCircle[i].lock.lock();
        myCircle[i].circleR = r;
        myCircle[i].lock.unlock();
    }
    void setCircleX(int i, float x) {
        myCircle[i].lock.lock();
        myCircle[i].circleX = x;
        myCircle[i].lock.unlock();
    }
    void setCircleY(int i, float y) {
        myCircle[i].lock.lock();
        myCircle[i].circleY = y;
        myCircle[i].lock.unlock();
    }
    void initCircle(int i){
        MyCircle circle = new MyCircle();
        circle.lock.lock();
        circle.circleR = (float) (Math.random() * 20 + 10);
        circle.circleX = (float) (Math.random() * width);
        circle.circleY = (float) (Math.random() * height);
        circle.angle = (float) (Math.random() * 360);
        circle.color = (int) (Math.random() * 0xffffff + 0xff000000);
        myCircle[i] = circle;
        circle.lock.unlock();
    }
    float getCircleR(int i) {
        return myCircle[i].circleR;
    }
    float getCircleX(int i) {
        return myCircle[i].circleX;
    }
    float getCircleY(int i) {
        return myCircle[i].circleY;
    }

    private void init() {
        myCircle = new MyCircle[number];
        for(int i=0;i<number;i++) {
            initCircle(i);
//            MyCircle circle = new MyCircle();
//            circle.lock.lock();
//            circle.circleR = (float) (Math.random() * 10 + 5);
//            circle.circleX = (float) (Math.random() * width);
//            circle.circleY = (float) (Math.random() * height);
//            circle.angle = (float) (Math.random() * 360);
//            circle.color = (int) (Math.random() * 0xffffff + 0xff000000);
//            circle.lock.unlock();
//            myCircle[i] = circle;
        }
    }

    private void drawCircle(Canvas canvas) {
        for (int i=0;i<number;i++) {
            paint.setColor(myCircle[i].color);
            canvas.drawCircle(myCircle[i].circleX,myCircle[i].circleY,myCircle[i].circleR,paint);
        }
    }

    private void circleChange() {
        for(int i=0;i<number;i++) {
            myCircle[i].lock.lock();
            if (time-- < 0) {
                myCircle[i].angle = (float) (myCircle[i].angle + (Math.random() * 5) - (Math.random() * 5)) % 360;
                time = 100;
            }
            float tx = (float) Math.cos(myCircle[i].angle) * dx;
            float ty = (float) Math.sin(myCircle[i].angle) * dx;
            myCircle[i].circleX += tx;
            myCircle[i].circleY += ty;
            if (myCircle[i].circleX < 0) {
                myCircle[i].angle += 180;
                myCircle[i].circleX = -myCircle[i].circleX;
            } else if (myCircle[i].circleX > width) {
                myCircle[i].angle += 180;
                myCircle[i].circleX = 2 * width - myCircle[i].circleX;
            }
            if (myCircle[i].circleY < 0) {
                myCircle[i].angle += 180;
                myCircle[i].circleY = -myCircle[i].circleY;
            } else if (myCircle[i].circleY > height) {
                myCircle[i].angle += 180;
                myCircle[i].circleY = 2 * height - myCircle[i].circleY;
            }
            myCircle[i].lock.unlock();
        }
    }

    private class MyCircle {
        Lock lock = new ReentrantLock();
        float circleR;
        float circleX;
        float circleY;
        float angle;
        int color;
    }
}
