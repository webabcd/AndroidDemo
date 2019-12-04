package com.webabcd.androiddemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    /**
     * assets 中的文本文件转字符串
     */
    public static String getAssetString(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 是否是非负整型
     */
    public static boolean isUInt(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    /**
     * 获取屏幕的尺寸
     */
    public static Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        windowManager.getDefaultDisplay().getSize(screenSize);

        return screenSize;
    }

    /**
     * 获取虚拟按键栏的高度
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }

        return result;
    }

    /**
     * 获取屏幕方向 Configuration.ORIENTATION_PORTRAIT 或 Configuration.ORIENTATION_LANDSCAPE
     */
    public static int getScreenOrientation(Context context) {
        return context.getResources().getConfiguration().orientation;
    }

    /**
     * dp 转 px
     */
    public static int dp2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        int pxValue = (int) (dpValue * scale + 0.5f);

        return pxValue;
    }

    /**
     * id 转 drawable
     */
    public static Drawable id2drawable(Context context, int drawableId) {
        return context.getResources().getDrawable(drawableId, null);
    }

    /**
     * 十进制整型转十六进制字符串
     */
    public static String int2Hex(int intValue) {
        return String.format("%02x", intValue);
    }

    /**
     * 字符串转字节数组
     */
    public static byte[] stringToBytes(String str)
    {
        byte[] destObj = null;
        try
        {
            if(null == str || str.trim().equals(""))
            {
                destObj = new byte[0];
                return destObj;
            }
            else
            {
                destObj = str.getBytes("UTF-8");
            }
        }
        catch (UnsupportedEncodingException e)
        {
            // e.printStackTrace();
        }
        return destObj;
    }

    /**
     * 字节数组转字符串
     */
    public static String bytesToString(byte[] bytes)
    {
        String str = null;
        try
        {
            str = new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // e.printStackTrace();
        }
        return str;
    }

    /**
     * 格式化时间
     */
    public static String formatDate(Date date, String pattern)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}

