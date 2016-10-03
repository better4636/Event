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
import android.view.View;
import android.widget.RelativeLayout;

public class DrawView extends View {
    Paint paint;
    private float x,y,dx,dy,ch,cw;
    private int select;  private Bitmap[] rabit;

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
            if(x >= MainActivity.drawPane.getWidth() - cw) {
                dx = -dx;
            }
            if(y >= MainActivity.drawPane.getHeight() - ch) {
                dy = -dy;
            }
            invalidate();
            mHandler.sendEmptyMessageDelayed(0,1000);
        }
    };
    /* Thread version 사용이 안된다 이유는 ?
     * 안드로이드에선 ui 즉 화면을 변경하는것은 오직 메인스레드에서만 가능하다.
     * Handler나 CountDownTimer 등이 있는 이유는 이것이다.
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
