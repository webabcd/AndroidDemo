/**
 * 本例用于演示如何实现支持回调的类
 *
 * 定义回调接口参见：CallbackDemo1_CallbackInterface.java
 * 使用支持回调的类参见：CallbackDemo1.java
 */

package com.webabcd.androiddemo.java;

import java.util.Date;

public class CallbackDemo1_CallbackClass {

    private CallbackDemo1_CallbackInterface _callback;

    public CallbackDemo1_CallbackClass(CallbackDemo1_CallbackInterface callBack){
        this._callback = callBack;
    }

    public void execute() {
        if (_callback != null) {
            if (new Date().getTime() % 2 == 0) {
                _callback.ok();
            } else {
                _callback.error();
            }
        }
    }
}
