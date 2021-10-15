/**
 * NDK 的简单示例
 *
 * 1、先配置 ndk-build
 * 在 File -> Settings -> Tools -> External Tools 中新增一项
 * name: ndk-build
 * Program: F:\Android\sdk\android-ndk-r23\ndk-build.cmd
 * Arguments: NDK_LIBS_OUT=D:\gitroot\AndroidDemo\app\src\main\jniLibs
 * Working directory: D:\gitroot\AndroidDemo\app\src\main
 *
 * 2、在 app\src\main 下新建 jni 目录（用于保存源代码文件）
 * 在 app\src\main 下新建 jniLibs 目录（用于保存编译后的 so 文件）
 *
 * 3、在 jni 文件夹上点击右键，然后单击 External Tools -> ndk-build 进行编译
 */

package com.webabcd.androiddemo.ndk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class NdkDemo1 extends AppCompatActivity {

    private TextView _txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_ndkdemo1);

        _txtMsg = this.findViewById(R.id.txtMsg);

        // 加载 so
        System.loadLibrary("jniHello");

        // java 调用 jni, c
        String resultC = helloJniC();
        _txtMsg.append(resultC);
    }

    // 调用 libjniHello.so 的 JNIEXPORT jstring JNICALL Java_com_webabcd_androiddemo_ndk_NdkDemo1_helloJniC(JNIEnv *env, jobject obj)
    public native String helloJniC();
}
