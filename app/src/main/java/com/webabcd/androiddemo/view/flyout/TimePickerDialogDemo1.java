/**
 * TimePickerDialog - 时间选择对话框
 *
 * 注1：TimePickerDialog 继承自 AlertDialog
 * 注2：实际项目中建议使用第三方的时间选择控件
 */

package com.webabcd.androiddemo.view.flyout;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.util.Calendar;

public class TimePickerDialogDemo1 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_timepickerdialogdemo1);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(TimePickerDialogDemo1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // 获取用户选择的时间
                        Toast.makeText(getApplicationContext(), String.format("%d:%d", hourOfDay, minute), Toast.LENGTH_SHORT).show();
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true);

                timePickerDialog.show();
            }
        });
    }
}
