package com.kimsangsu.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
public class DrawView extends View {

    private Paint p ;
    public DrawView(Context context) {
        super(context);
        p = new Paint();
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
    }
}
