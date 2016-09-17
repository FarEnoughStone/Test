package com.kui.test;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private RelativeLayout rl = null;
    private int number = 10;
    private int width, height;
    private CanvasOfCircle circles;
    private UserCircle userCircle;
    private NPCCircle npcCircle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        rl = (RelativeLayout) findViewById(R.id.rl);
        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        circles = new CanvasOfCircle(this,width,height);
        rl.addView(circles);
        userCircle = drawUserCircle();
        npcCircle = new NPCCircle(this, width, height, circles);
        rl.addView(npcCircle);
        new MyThread().start();
    }

    class MyThread extends Thread {
        public void run() {
            try {
                while (true){
                    float x = userCircle.getCircleX();
                    float y = userCircle.getCircleY();
                    float r = userCircle.getCircleR();
                    float x0 = x - r;
                    float x1 = x + r;
                    float y0 = y - r;
                    float y1 = y + r;
                    for(int i=0;i<number;i++) {
                        if(x0 < circles.getCircleX(i) && x1 > circles.getCircleX(i) &&
                                y0 < circles.getCircleY(i) && y1 > circles.getCircleY(i)){
                            userCircle.addCircleR(circles.getCircleR(i));
                            circles.initCircle(i);
                        }
                    }
                    if (x0 < npcCircle.getCircleX() && x1 > npcCircle.getCircleX() &&
                            y0 < npcCircle.getCircleY() && y1 > npcCircle.getCircleY()){
                        if (r > npcCircle.getCircleR()) {
                            userCircle.addCircleR(npcCircle.getCircleR() / 2);
                            npcCircle.beEat = true;
                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("GAME OVER")
                                    .setNegativeButton("重新开始", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            npcCircle.beEat = true;
                                            userCircle.setCircleR(50);
                                        }
                                    })
                                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        }
                    }
                }
            } catch (Exception e) {
                (Toast.makeText(MainActivity.this,"线程002出错！",Toast.LENGTH_SHORT)).show();
            }
        }
    }

    private UserCircle drawUserCircle() {
        UserCircle circle = new UserCircle(this,width,height);
        circle.setId(View.generateViewId());
        rl.addView(circle);
        return circle;
    }
}
