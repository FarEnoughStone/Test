package com.kui.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CanvasOfCircle extends View {
    private MyCircle myCircle[];
    private int width, height;
    private Paint paint;
    private int time,TIME = 50;
    private int dx = 3;
    final int number = 10;

    /**
     * 构造函数
     * @param context
     * @param w
     * @param h
     */
    public CanvasOfCircle(Context context, int w, int h) {
        super(context);
        width = w;
        height = h;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);//抗锯齿
        myCircle = new MyCircle[number];
        for(int i=0;i<number;i++) {
            initCircle(i);
        }
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        for (int i=0;i<number;i++) {
            paint.setColor(myCircle[i].color);
            canvas.drawCircle(myCircle[i].circleX,myCircle[i].circleY,myCircle[i].circleR,paint);
        }
        circleChange();
        invalidate();//刷新画布
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
        circle.color = (int) (Math.random() * 0xffffff + 0xcc000000);
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

    private void circleChange() {
        for(int i=0;i<number;i++) {
            myCircle[i].lock.lock();
            if (--time < 0) {
                myCircle[i].angle = (float) ((myCircle[i].angle + Math.random() * 3 - Math.random() * 3) % Math.PI);
                time = TIME;
            }
            float tx = (float) Math.cos(myCircle[i].angle) * dx;
            float ty = (float) Math.sin(myCircle[i].angle) * dx;
            myCircle[i].circleX += tx;
            myCircle[i].circleY += ty;
            if (myCircle[i].circleX < 0) {
                myCircle[i].angle += Math.PI;
                time = 0;
                myCircle[i].circleX = -myCircle[i].circleX;
            } else if (myCircle[i].circleX > width) {
                myCircle[i].angle += Math.PI;
                time = 0;
                myCircle[i].circleX = 2 * width - myCircle[i].circleX;
            }
            if (myCircle[i].circleY < 0) {
                myCircle[i].angle += Math.PI;
                time = 0;
                myCircle[i].circleY = -myCircle[i].circleY;
            } else if (myCircle[i].circleY > height) {
                myCircle[i].angle += Math.PI;
                time = 0;
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
