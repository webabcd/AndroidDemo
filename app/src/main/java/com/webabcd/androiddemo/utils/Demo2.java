/**
 * 演示获取唯一标识的各种方法
 */

package com.webabcd.androiddemo.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.webabcd.androiddemo.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Demo2 extends AppCompatActivity {

    private TextView _textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils_demo2);

        _textView1 = findViewById(R.id.textView1);

        sample();
    }

    private void sample() {

        // 判断 wifi 是否打开（wifi 没有打开的话是拿不到 wifi 的 mac 地址的）
        // 如果获取到的 mac 地址是 02:00:00:00:00:00 或 00:00:00:00:00:00 则请认为他是无效的
        _textView1.append("wifi enabled: " + isWifiEnabled());
        _textView1.append("\n");
        // Android 6 以下获取 mac 地址（api level < 23）
        _textView1.append("mac(api level < 23): " + getMac1());
        _textView1.append("\n");
        // Android 6 获取 mac 地址（api level == 23）
        _textView1.append("mac(api level == 23): " + getMac2());
        _textView1.append("\n");
        // Android 7 或以上获取 mac 地址（api level >= 24）
        _textView1.append("mac(api level >= 24): " + getMac3());
        _textView1.append("\n");


        // 获取 ANDROID_ID（设备首次 boot 时生成，刷机的话是会变的）
        // 如果获取到的是 9774d56d682e549c 则请认为他是无效的（这是一部分手机的 bug，他们都会返回这个值）
        String androidId = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        _textView1.append("android id: " + androidId);
        _textView1.append("\n");


        // 获取手机的一些信息，可以把它们哈希一下作为唯一标识，但是似乎没啥用
        String phoneInfo = String.format(Locale.ENGLISH, "board:%s, brand:%s, supported_abis:%s, device:%s, display:%s, host:%s, " +
                "id:%s, manufacturer:%s, model:%s, product:%s, tags:%s, type:%s, user:%s, time:%d",
                Build.BOARD, Build.BRAND, Arrays.toString(Build.SUPPORTED_ABIS), Build.DEVICE, Build.DISPLAY, Build.HOST,
                Build.ID, Build.MANUFACTURER, Build.MODEL, Build.PRODUCT, Build.TAGS, Build.TYPE, Build.USER, Build.TIME);
        _textView1.append("phone info: " + phoneInfo);
        _textView1.append("\n");


        // 获取 Google Advertising ID（这个 adid 用户是可以重置的）
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 先在 build.gradle 中配置 implementation 'com.google.android.gms:play-services:x.x.x'
                    // 然后通过如下方式获取 adid（注：不能在主线程获取）
                    // 如果你的设备安装了 google play services 则可以获取到 adid，反之则会收到异常信息
                    AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(Demo2.this);
                    String adid = info.getId();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _textView1.append("adid: " + adid);
                            _textView1.append("\n");
                        }
                    });
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _textView1.append("get adid error: " + ex.toString());
                            _textView1.append("\n");
                        }
                    });
                }
            }
        });
        thread.setDaemon(true);
        thread.setName("Demo2_thread");
        thread.start();
    }


    /**
     * 检查 wifi 是否打开
     */
    private boolean isWifiEnabled() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return null != wifiManager && wifiManager.isWifiEnabled();
    }

    /**
     * Android 6 以下获取 mac 地址（api level < 23）
     * 需要权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     */
    public String getMac1() {
        String mac = "";

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception ex) {
            return ex.toString();
        }

        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }

        return mac;
    }

    /**
     * Android 6 获取 mac 地址（api level == 23）
     */
    public String getMac2() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            while (null != str) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (IOException ex) {
            return ex.toString();
        }

        if (!TextUtils.isEmpty(macSerial)) {
            macSerial = macSerial.toUpperCase(Locale.ENGLISH);
        }

        return macSerial;
    }

    /**
     * Android 7 或以上获取 mac 地址（api level >= 24）
     * 需要权限 <uses-permission android:name="android.permission.INTERNET" />
     */
    public String getMac3() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0"))
                    continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null)
                    return "";
                StringBuilder res1 = new StringBuilder();
                for (Byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (!TextUtils.isEmpty(res1)) {
                    res1.deleteCharAt(res1.length() - 1);
                }

                return res1.toString().toUpperCase(Locale.ENGLISH);
            }
        } catch (Exception ex) {
            return ex.toString();
        }

        return "";
    }
}