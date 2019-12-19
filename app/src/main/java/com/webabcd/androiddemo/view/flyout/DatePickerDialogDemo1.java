/**
 * DatePickerDialog - 日期选择对话框
 *
 * 注1：DatePickerDialog 继承自 AlertDialog
 * 注2：实际项目中建议使用第三方的日期选择控件
 */

package com.webabcd.androiddemo.view.flyout;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.webabcd.androiddemo.R;

import java.util.Calendar;

public class DatePickerDialogDemo1 extends AppCompatActivity {

    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flyout_datepickerdialogdemo1);

        mButton1 = findViewById(R.id.button1);

        sample();
    }

    private void sample() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(DatePickerDialogDemo1.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 获取用户选择的日期（注：月份要加上 1）
                        Toast.makeText(DatePickerDialogDemo1.this, String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth), Toast.LENGTH_SHORT).show();
                    }
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
    }
}
