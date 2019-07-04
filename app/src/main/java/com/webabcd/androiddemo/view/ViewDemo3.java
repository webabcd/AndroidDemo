package com.webabcd.androiddemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewDemo3 extends AppCompatActivity {

    private ImageView _imageView1;

    private EditText scaleEt;
    private EditText rotateEt;
    private EditText translateEt1;
    private EditText translateEt2;
    private ImageView imageView;
    private Matrix matrix;
    private Bitmap bitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_viewdemo3);

        _imageView1 = (ImageView)findViewById(R.id.imageView1);



     //   bitMap = BitmapFactory.decodeResource(getResources(), R.drawable.img_sample_son);
      //  imageView.setImageBitmap(bitMap);
      //  matrix = new Matrix();

        sample();
    }

    private void sample() {
        Matrix matrix = new Matrix();
        matrix.setScale(5, 5);
      //  matrix.setRotate(30);
      _imageView1.setImageMatrix(matrix);
    }

    public void scaleBitmap(View view){
        matrix.postScale(getValues(scaleEt), getValues(scaleEt));
        imageView.setImageMatrix(matrix);
    }

    public void rotateBitmap(View view){
        matrix.postRotate(getValues(rotateEt));
        imageView.setImageMatrix(matrix);
    }

    public void translateBitmap(View view){
        matrix.postTranslate(getValues(translateEt1), getValues(translateEt2));
        imageView.setImageMatrix(matrix);
    }

    public void clearMatrix(View view){
        matrix.reset();
        imageView.setImageMatrix(matrix);
    }

    private float getValues(EditText et){
        return Float.parseFloat(et.getText().toString());
    }
}
