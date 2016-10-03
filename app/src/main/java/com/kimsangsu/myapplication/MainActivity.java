package com.kimsangsu.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout drawPane;
    private DrawView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawPane = (RelativeLayout) findViewById(R.id.drawPane);
        drawView = new DrawView(this);
        drawPane.addView(drawView);
    }
}
