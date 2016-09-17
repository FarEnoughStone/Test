package com.kui.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 2016/9/3.
 */
public class Circle extends View{

    private int circleR,diameter;
    private int circleX, circleY;
    private int width, height;
    private double oldAngle;
    private Thread thread;
    private Paint paint;

    public Circle(Context context, final int sw, final int sh) {
        super(context);
        width = sw;
        height = sh;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setColor((int) (Math.random() * 0xffffff) + 0xcc000000);
        //paint.setColor(0xff00ff00);
        circleR = (int)(Math.random() * 50 + 20);
        oldAngle = (Math.random() * 360 + 1);
        thread = new Thread() {
            @Override
            public void run() {
                t(Circle.this, sw, sh);
            }
        };
        thread.start();
    }

    public int getCircleR() {
        return circleR;
    }

    public int getCircleX() {
        return circleX;
    }

    public int getCircleY() {
        return circleY;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        diameter = circleR * 2;
        setMeasuredDimension(diameter, diameter);
    }

    @Override
    public void onDraw(Canvas canvas) {
        diameter = circleR * 2;
        canvas.drawCircle(diameter/2,diameter/2,circleR,paint);
    }

    private void t(View image, final int sw, final int sh) {
        final View imageView = image;
        oldAngle = (oldAngle + (Math.random() * 2) - (Math.random() * 2)) % 360;
        float tx = (float) Math.cos(oldAngle) * 20;
        float ty = (float) Math.sin(oldAngle) * 20;
        float x = imageView.getLeft();
        float y = imageView.getTop();
        if (x < 0 ) {
            tx = Math.abs(tx);
        }else if (x > sw) {
            tx = -Math.abs(tx);
        }
        if (y < 0 ) {
            ty = Math.abs(ty);
        }else if (y > sh) {
            ty = -Math.abs(ty);
        }
        final int ftx = (int) tx, fty = (int) ty;
        TranslateAnimation translateAnimation =
                new TranslateAnimation(0,tx, 0, ty);
        translateAnimation.setDuration(1000);
        translateAnimation.setInterpolator(new LinearInterpolator());
        //添加了这行代码的作用时，view移动的时候 会有弹性效果
        //translateAnimation.setInterpolator(new OvershootInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                int left = imageView.getLeft() + ftx;
                int top  = imageView.getTop() + fty;
                int width = imageView.getWidth();
                int height = imageView.getHeight();
                circleX = left + circleR;
                circleY = top + circleR;
                imageView.clearAnimation();
                imageView.layout(left, top, left+width, top+height);
                t(imageView,sw,sh);
            }
        });
        imageView.startAnimation(translateAnimation);
    }

    public void stopThread() {
        thread.stop();
    }
}
