/**
 * 用于获取软键盘的高度
 *
 * 原理就是使用一个全屏的 PopupWindow，当弹出软键盘时其会收缩，然后根据其收缩后的大小来计算软键盘的高度
 *
 * 本类的使用请参见 view/text/EditTextDemo3.java
 */

package com.webabcd.androiddemo.view.text.utils;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.webabcd.androiddemo.R;
import com.webabcd.androiddemo.utils.Helper;

/**
 * 继承自 PopupWindow
 */
public class KeyboardHeightProvider extends PopupWindow {
    private KeyboardHeightObserver _observer;
    private View _popupWindowView;
    private View _rootView;
    private Activity _activity;

    // 用于保存当前软键盘的高度
    public int softInputHeight = 0;

    public KeyboardHeightProvider(Activity activity) {
        super(activity);
        _activity = activity;

        // 指定此 PopupWindow 对应的布局文件
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        _popupWindowView = inflater.inflate(R.layout.popupwindow_measure_softinput, null, false);
        setContentView(_popupWindowView);

        _rootView = activity.findViewById(android.R.id.content);

        // 弹出此 PopupWindow 时需要显示软键盘
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        // SOFT_INPUT_ADJUST_RESIZE - 调整 PopupWindow 的尺寸，以避免其被输入法遮挡（即当输入法弹出时，PopupWindow 会被顶上去）
        // SOFT_INPUT_STATE_ALWAYS_VISIBLE - 当 PopupWindow 获取到焦点时总是显示软键盘
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        // 指定此 PopupWindow 的宽度为 0 高度为全屏（宽度为 0 是为了避免 PopupWindow 遮挡其他内容，高度为全屏是为了计算软键盘的高度）
        setWidth(0);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        // 此 PopupWindow 的布局发生变化时（比如软件盘的显示和隐藏）
        _popupWindowView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (_popupWindowView != null) {
                    // 计算并通知软键盘的高度
                    handleOnGlobalLayout();
                }
            }
        });
    }

    /**
     * 启动 KeyboardHeightProvider（必须在 Activity 的 onResume 完成之后调用，否则无法注册 PopupWindow）
     */
    public void start() {
        if (!isShowing() && _rootView.getWindowToken() != null) {
            setBackgroundDrawable(new ColorDrawable(0));

            // 显示 PopupWindow
            showAtLocation(_rootView, Gravity.NO_GRAVITY, 0, 100);
        }
    }

    /**
     * 关闭 KeyboardHeightProvider
     */
    public void close() {
        this._observer = null;

        // 关闭 PopupWindow
        dismiss();
    }

    /**
     * 指定一个 KeyboardHeightObserver 对象，用于接收软键盘高度的变化的通知
     */
    public void setKeyboardHeightObserver(KeyboardHeightObserver observer) {
        this._observer = observer;
    }

    /**
     * 计算并通知软键盘的高度
     */
    private void handleOnGlobalLayout() {
        // 此 PopupWindow 的实际高度
        Rect popupRect = new Rect();
        _popupWindowView.getWindowVisibleDisplayFrame(popupRect);
        int popupHeight = popupRect.bottom;

        // 屏幕的高度
        Point screenSize = Helper.getScreenSize(_activity);
        int screenHeight = screenSize.y;

        // 虚拟按键栏的高度
        int navigationBarHeight = Helper.getNavigationBarHeight(_activity);

        // 计算并通知软键盘的高度
        softInputHeight = 0;
        if (screenHeight != popupHeight) {
            // 软键盘的高度等于屏幕的高度减去 PopupWindow 的高度（PopupWindow 本来是全屏的，但是由于指定了 WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE 所以当输入法弹出时，PopupWindow 会被顶上去）再减去虚拟按键栏的高度
            softInputHeight = screenHeight - popupHeight  - navigationBarHeight;
        }
        notifyKeyboardHeightChanged(softInputHeight);
    }

    /**
     * 用于通知软键盘的高度
     */
    private void notifyKeyboardHeightChanged(int height) {
        if (_observer != null) {
            _observer.onKeyboardHeightChanged(height);
        }
    }
}