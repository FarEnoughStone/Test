package com.kui.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/9/13.
 */
public class UserCircle extends View {

    private int width, height;
    private float circleX, circleY, circleR;
    private float keyX, keyY;
    private Paint paint,keyPaint;
    private float tx, ty;
    private int KEY_LOAD = 250;

    public UserCircle(Context context, int w, int h) {
        super(context);
        width = w;
        height = h;
        circleX = width / 2;
        circleY = height / 2;
        circleR = 50;
        keyX = keyY = KEY_LOAD;
        paint = new Paint();
        paint.setColor((int) (Math.random() * 0xffffff + 0xcc000000));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        keyPaint = new Paint();
        keyPaint.setColor(0x99000000 + 0x87CEEB);
        keyPaint.setAntiAlias(true);
        keyPaint.setStrokeWidth(3);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        changeLoad(event.getX(),event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        tx = ty = 0;
                        keyX = keyY = KEY_LOAD;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void onDraw(Canvas canvas) {
        circleX += tx;
        circleY += ty;
        if (circleX < 0) {
            circleX = 0;
        } else if (circleX > width) {
            circleX = width;
        }
        if (circleY < 0) {
            circleY = 0;
        } else if (circleY > height) {
            circleY = height;
        }
        canvas.drawCircle(circleX,circleY,circleR,paint);
        keyPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(circleX,circleY,circleR/2,keyPaint);
        canvas.drawCircle(keyX, keyY, 70, keyPaint);
        keyPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(KEY_LOAD, KEY_LOAD, 150, keyPaint);
        invalidate();
    }

    private void changeLoad(float x, float y) {
        float dx = 100 / circleR;
        double z = Math.sqrt((x - KEY_LOAD) * (x - KEY_LOAD) + (y - KEY_LOAD) * (y - KEY_LOAD));
        double SIN = (y - KEY_LOAD) / z;
        double COS = (x - KEY_LOAD) / z;
//        if (x > KEY_LOAD) {
//        使用角度时，当tan值过大，甚至使double型溢出，造成角度不足。
//            angle = Math.tanh((y - KEY_LOAD) / (x - KEY_LOAD));
//        } else {
//            angle = Math.tanh((y - KEY_LOAD) / (x - KEY_LOAD)) + Math.PI;
//        }
//        tx = (float) Math.cos(angle) * dx;
//        ty = (float) Math.sin(angle) * dx;
//        keyX = KEY_LOAD + (float) Math.cos(angle) * 80;
//        keyY = KEY_LOAD + (float) Math.sin(angle) * 80;
        tx = (float) COS * dx;
        ty = (float) SIN * dx;
        keyX = KEY_LOAD + (float) COS * 80;
        keyY = KEY_LOAD + (float) SIN * 80;
    }

    void addCircleR(float r) {
        circleR = (float) Math.sqrt(circleR * circleR + r * r);
    }
    void setCircleR(float r) {
        circleR = r;
    }
    float getCircleR() {
        return circleR;
    }
    float getCircleX() {
        return circleX;
    }
    float getCircleY() {
        return circleY;
    }
}
