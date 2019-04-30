/**
 * SeekBar - 拖动条
 *     setOnSeekBarChangeListener(OnSeekBarChangeListener l) - 拖动条发生改变时的回调
 * OnSeekBarChangeListener
 *     onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) - 进度值发生变化
 *         progress - 当前进度值
 *         fromUser - 是否是用户操作导致的进度变化（比如用户拖动了进度则此值为 true；程序修改了进度则此值为 false）
 *     onStartTrackingTouch(SeekBar seekBar) - 拖动开始
 *     onStopTrackingTouch(SeekBar seekBar) - 拖动结束
 */

package com.webabcd.androiddemo.view.progress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

public class SeekBarDemo1 extends AppCompatActivity {

    private TextView _textView1;
    private SeekBar _seekBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progress_seekbardemo1);

        _textView1 = (TextView)findViewById(R.id.textView1);
        _seekBar1 = (SeekBar)findViewById(R.id.seekBar1);

        sample();
    }

    private void sample() {
        _seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                _textView1.setText(String.format("progress:%d, fromUser:%b", progress, fromUser));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "start tracking touch", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "stop tracking touch", Toast.LENGTH_SHORT).show();
            }
        });

        // 在 java 中设置拖动条的进度
        _seekBar1.setProgress(30);
    }
}
