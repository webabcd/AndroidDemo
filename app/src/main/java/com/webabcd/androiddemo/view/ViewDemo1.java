/**
 * 演示 view 位置相关的知识点（注：左上角点为原点）
 *
 * getLeft() - 左侧边相对于父容器左侧边的距离（对应 layout_marginLeft 设置的值）
 * getTop() - 上侧边相对于父容器上侧边的距离（对应 layout_marginTop 设置的值）
 * getRight() - 右侧边相对于父容器左侧边的距离，其值等于 getLeft() + getWidth()
 * getBottom() - 下侧边相对于父容器上侧边的距离，其值等于 getTop() + getHeight()
 * 注：不要用 setLeft(), setTop(), setRight(), setBottom() 来指定 view 的位置，因为它们是由布局系统调用的
 *
 * getTranslationX(), setTranslationX() - 向 X 轴方向移动的距离（不会改变 left, top, right, bottom 的值）
 * getTranslationY(), setTranslationY() - 向 Y 轴方向移动的距离（不会改变 left, top, right, bottom 的值）
 * getX(), setX() - 左侧边相对于父容器左侧边的实际距离，其值等于 getLeft() + getTranslationX()
 * getY(), setY() - 上侧边相对于父容器上侧边的实际距离，其值等于 getTop() + getTranslationY()
 *
 * scrollTo(int x, int y) - 将 view 中的内容（注：不是 view 本身，而是 view 中的内容）移动到指定的 -x 和 -y
 * scrollBy(int x, int y) - 将 view 中的内容（注：不是 view 本身，而是 view 中的内容）移动指定的 -x 和 -y
 */

package com.webabcd.androiddemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ViewDemo1 extends AppCompatActivity {

    private TextView _view1;
    private TextView _view2;
    private TextView _textView1;
    private TextView _textView2;
    private Button _button1;
    private Button _button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo1);

        _view1 = (TextView) findViewById(R.id.view1);
        _view2 = (TextView) findViewById(R.id.view2);
        _textView1 = (TextView) findViewById(R.id.textView1);
        _textView2 = (TextView) findViewById(R.id.textView2);
        _button1 = (Button) findViewById(R.id.button1);
        _button2 = (Button) findViewById(R.id.button2);

        sample();
    }

    private void sample() {
        _view2.setTranslationX(100);
        _view2.setTranslationY(50);

        // 监听当前根视图的 OnPreDrawListener 事件（在 draw 的时候各个 view 的位置和宽高都确定了，此时便可以获取各个 view 的真实的位置信息和宽高信息）
        getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // 移除当前根视图的 OnPreDrawListener 事件的监听
                getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);

                _textView1.setText(String.format("view1 left:%d, top:%d, right:%d, bottom:%d, x:%.2f, y:%.2f, translationX:%.2f, translationY:%.2f, width:%d, height:%d",
                        _view1.getLeft(), _view1.getTop(), _view1.getRight(), _view1.getBottom(), _view1.getX(), _view1.getY(), _view1.getTranslationX(), _view1.getTranslationY(), _view1.getWidth(), _view1.getHeight()));

                _textView2.setText(String.format("view2 left:%d, top:%d, right:%d, bottom:%d, x:%.2f, y:%.2f, translationX:%.2f, translationY:%.2f, width:%d, height:%d",
                        _view2.getLeft(), _view2.getTop(), _view2.getRight(), _view2.getBottom(), _view2.getX(), _view2.getY(), _view2.getTranslationX(), _view2.getTranslationY(), _view2.getWidth(), _view2.getHeight()));

                return true;
            }
        });

        _button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将 view 中的内容移动到 0,0 的位置
                // 注：移动的不是 view 而是 view 中的内容；移动到 -x, -y 而不是 x, y
                _view2.scrollTo(0, 0);
            }
        });

        _button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将 view 中的内容 x 方向移动 5，y 方向移动 5
                // 注：移动的不是 view 而是 view 中的内容；移动 -x, -y 而不是 x, y
                _view2.scrollBy(-5, -5);
            }
        });
    }
}
