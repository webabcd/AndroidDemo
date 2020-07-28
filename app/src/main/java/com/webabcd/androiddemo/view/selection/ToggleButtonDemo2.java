/**
 * ToggleButton - 双状态按钮
 *     setButtonDrawable() - 设置双状态按钮在各种状态下的样式
 */

package com.webabcd.androiddemo.view.selection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ToggleButton;

import com.webabcd.androiddemo.R;

public class ToggleButtonDemo2 extends AppCompatActivity {

    private ToggleButton _toggleButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_togglebuttondemo2);

        _toggleButton2 = (ToggleButton)findViewById(R.id.toggleButton2);
        _toggleButton2.setButtonDrawable(R.drawable.selector_togglebutton_button);
    }
}
