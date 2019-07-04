package com.webabcd.androiddemo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.webabcd.androiddemo.R;

public class ActivityDemo1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(new MyView(this));
    }

    /*
    class MyView extends View {
        private Bitmap bitMap;
        private Matrix matrix;
        public MyView(Context context) {
            super(context);
            matrix = new Matrix();
            bitMap = ((BitmapDrawable) getResources().getDrawable(R.drawable.icon)).getBitmap();
            matrix.setScale(100f/bitMap.getWidth(), 100f/bitMap.getHeight());
            matrix.postTranslate(150, 150);
            matrix.postSkew(0.2f,0.2f,150,150);//拉伸

        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(bitMap, matrix, null);
        }
    }
    */
}
