/**
 * ImageButton - 图片按钮控件（继承自 ImageView）
 *     setClickable(boolean clickable) - 指定按钮是否可单击（如果要指定为 false 的话，需要在 setOnClickListener() 方法之后指定才会生效）
 *     setOnClickListener(OnClickListener l) - 指定单击事件的回调
 *
 *
 * ImageButton 和 Button 差不多（就是基类不一样，一个是 TextView 一个是 ImageView），详见 Button 的介绍吧
 */

package com.webabcd.androiddemo.view.button;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class ImageButtonDemo1 extends AppCompatActivity {

    private ImageButton _imageButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_button_imagebuttondemo1);

        _imageButton1 = (ImageButton) findViewById(R.id.imageButton1);

        sample();
    }

    private void sample() {
        _imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImageButtonDemo1.this, "imageButton1 clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
