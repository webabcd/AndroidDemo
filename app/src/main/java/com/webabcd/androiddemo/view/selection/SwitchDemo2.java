/**
 * Switch - 状态切换控件
 *     setThumbResource() - thumb 的各种状态下的样式
 *     setTrackResource() - track 的各种状态下的样式
 *     setSwitchTextAppearance() - 文字（在 textOff 和 textOn 设置的文字）的样式
 */

package com.webabcd.androiddemo.view.selection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import com.webabcd.androiddemo.R;

public class SwitchDemo2 extends AppCompatActivity {

    private Switch _switch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection_switchdemo2);

        _switch2 = (Switch)findViewById(R.id.switch2);

        sample();
    }

    private void sample() {
        _switch2.setThumbResource(R.drawable.selector_switch_thumb);
        _switch2.setTrackResource(R.drawable.selector_switch_track);
        _switch2.setSwitchTextAppearance(this, R.style.SwitchTextAppearance);
    }
}
