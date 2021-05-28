/**
 * 单例模式
 */

package com.webabcd.androiddemo.designpattern;

import android.os.SystemClock;

public class SingletonPattern {
    private static SingletonPattern instance = null;

    public synchronized static SingletonPattern getInstance() {
        if (instance == null) {
            instance = new SingletonPattern();
        }
        return instance;
    }

    private final long mTimestamp;

    private SingletonPattern() {
        mTimestamp = SystemClock.elapsedRealtime();
    }

    public long getTimestamp() {
        return mTimestamp;
    }
}
