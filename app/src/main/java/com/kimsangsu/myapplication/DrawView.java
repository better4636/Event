package com.kimsangsu.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DrawView extends View implements View.OnTouchListener,View.OnClickListener{
    Paint paint;
    private float x,y,dx,dy,ch,cw;
    private int select;  private Bitmap[] rabit;
    private Context context;
    private Toast t;


    public DrawView(Context context) {
        super(context);
        rabit = new Bitmap[2];
        rabit[0] = BitmapFactory.decodeResource(getResources(),R.drawable.rabbit);
        rabit[1] = BitmapFactory.decodeResource(getResources(),R.drawable.rabbit2);
        x = MainActivity.drawPane.getLeft(); y= MainActivity.drawPane.getTop();
        ch = rabit[0].getHeight(); cw = rabit[0].getWidth();
        select = 0; dx = 30; dy = 30;
        paint = new Paint();
        mHandler.sendEmptyMessage(0);
        this.context = context; t = new Toast(context);
        t = Toast.makeText(context,"start",Toast.LENGTH_SHORT);
    }
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        canvas.drawBitmap(rabit[select],x,y,paint);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            x += dx; y += dy;
                if (x < 0||x >= MainActivity.drawPane.getWidth() - cw) {
                    dx = -dx;
                }
                if (y < 0||y >= MainActivity.drawPane.getHeight() - ch) {
                    dy = -dy;
                }
            select = (select+1)%2;
            invalidate();
            mHandler.sendEmptyMessageDelayed(0,1000);
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(x == event.getX() && x == event.getY()) {
                x = event.getX();
                y = event.getY();
            }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            x = event.getX();
            y = event.getY();
            mHandler.removeCallbacksAndMessages(null);
            invalidate();
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            mHandler.sendEmptyMessage(0);
        }

        return false;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fastBtn:
                if(Math.abs(dx) != 150) {
                    if(dx > 0) {
                        dx += 5;
                        dy += 5;
                    }
                    else {
                        dx -= 5;
                        dy -= 5;
                    }
                    t.setText("Speed Up = " +Math.abs(dx));

                }
                else {
                    t.setText("Max Speed !");
                }
                break;
            case R.id.slowBtn:
                if(Math.abs(dx) != 0) {
                    if(dx > 0) {
                        dy -= 5;
                        dx -= 5;
                    }
                    else {
                        dy += 5;
                        dx += 5;
                    }
                    t.setText("Speed Down = " +Math.abs(dx));
                }
                else {
                    t.setText("Min Speed ~~");
                }
                break;
        }
        t.show();

    }


    /* Thread version 사용이 안된다 이유는 ?
     * 안드로이드에선 ui 즉 화면을 변경하는것은 오직 메인스레드에서만 가능하다.
     * Handler나 CountDownTimer 등의 서브스레드가 있는 이유는 이것이다.
     */
    /*
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            }catch (Exception e) {
            }finally {
                x += dx;
                y += dy;
                invalidate();
            }
        }
    }*/
}
