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

public class DrawView extends View implements View.OnTouchListener,View.OnClickListener,Runnable{
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
        Thread thread = new Thread(this);
        thread.start();
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        canvas.drawBitmap(rabit[select],x,y,paint);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1)
                invalidate();
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
            if (event.getX() >= MainActivity.drawPane.getWidth() - cw) {
                x = MainActivity.drawPane.getWidth()-cw;
            }
            if(event.getX() < 0) {
                x = 0;
            }
            if (y >= MainActivity.drawPane.getHeight() - ch) {
                y = MainActivity.drawPane.getHeight()-ch;
            }
            if(y < 0) {
                y = 0;
            }
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

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            }catch (Exception e) {

            }finally {
                x += dx; y += dy;
                //벽에 닿으면 캐릭터의 방향을 바꾼다.
                if (x < 0 || x >= MainActivity.drawPane.getWidth() - cw) {
                    dx = -dx;
                }
                if (y < 0 || y >= MainActivity.drawPane.getHeight() - ch) {
                    dy = -dy;
                }
                select = (select+1)%2;
                Message message = Message.obtain();
                message.what =1;
                mHandler.sendMessage(message);
            }
        }
    }
}
