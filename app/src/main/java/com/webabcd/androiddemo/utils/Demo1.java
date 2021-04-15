/**
 * 在 KeyStore 中保存秘钥（你不必再关心秘钥如何保存，以及如何保证秘钥的安全了），秘钥是无法导出的（所以很安全），app 卸载后秘钥会被自动清除
 */

package com.webabcd.androiddemo.utils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class Demo1 extends AppCompatActivity {

    private String _keyStoreProvider = "AndroidKeyStore"; // 固定值（在 KeyStore 中保存秘钥）
    private String _keyAlias = "myKey"; // 秘钥的别名

    private TextView _textView1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils_demo1);

        _textView1 = findViewById(R.id.textView1);

        // 我这个例子主要是借用 KeyStore 生成一个 16 字节的数据（这样做的意义就是，由于我拿不到 KeyStore 中的秘钥的值，所以我可以用这个 16 字节的结果当做秘钥，其也是很安全的）
        try {
            // 加密一个 3 字节的数据，指定 gcm 标签长度为 104，所以会得到一个 16 字节的结果
            String xxx = "abc";

            byte[] resultBytes = encrypt(xxx.getBytes("UTF-8"));
            String resultString = Helper.bytesToHexString(resultBytes);
            _textView1.append("加密后的数据：" + resultString);
            _textView1.append("\n");

            resultBytes = decrypt(resultBytes);
            resultString = Helper.bytesToString(resultBytes);
            _textView1.append("解密后的数据：" + resultString);

        } catch (Exception ex) {
            _textView1.setText(ex.toString());
        }
    }

    // 加密
    @RequiresApi(api = Build.VERSION_CODES.M)
    private byte[] encrypt(byte[] input) {
        try {
            byte[] iv = new byte[12];
            for (int i = 0; i < iv.length; i ++)
            {
                iv[i] = (byte)(100);
            }

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            // 第 1 个参数用于指定 gcm 的标签长度（可能的值有：96, 104, 112, 120, 128）
            //   我这里指定了标签长度为 104 也就是说如果加密空字节数据，则加密结果为 13 个字节，如果加密 3 个字节的数据，则加密结果为 16 个字节
            // 第 2 个参数用于指定一个 12 字节的 iv，默认是不支持手动指定的，每次都随机生成，通过 cipher.getIV() 获取
            //   a) 如果你手动指定了 iv 则会收到 Caller-provided IV not permitted 错误
            //   b) 如果非要手动指定 iv 的话，则在构造 KeyGenParameterSpec 对象的时候要加上 setRandomizedEncryptionRequired(false)
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(104, iv);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), gcmParameterSpec);
            byte[] output = cipher.doFinal(input);

            return output;
        } catch (Exception ex) {
            _textView1.setText(ex.toString());
            return null;
        }
    }

    // 解密
    @RequiresApi(api = Build.VERSION_CODES.M)
    private byte[] decrypt(byte[] input) {
        try {
            byte[] iv = new byte[12];
            for (int i = 0; i < iv.length; i ++)
            {
                iv[i] = (byte)(100);
            }

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(104, iv);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), gcmParameterSpec);
            byte[] output = cipher.doFinal(input);

            return output;
        } catch (Exception ex) {
            _textView1.setText(ex.toString());
            return null;
        }
    }

    // 从 KeyStore 中获取指定别名的 SecretKey，如果没有则新生成一个
    @RequiresApi(api = Build.VERSION_CODES.M)
    private SecretKey getSecretKey() {
        SecretKey secretKey = loadSecretKey();
        if (secretKey == null) {
            secretKey = generateSecretKey();
        }
        return secretKey;
    }

    // 在 KeyStore 中生成指定别名的 SecretKey
    @RequiresApi(api = Build.VERSION_CODES.M)
    private SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, _keyStoreProvider);
            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(_keyAlias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(false) // 加密的时候需要支持手动指定 iv（默认是不支持的）
                    .build();
            keyGenerator.init(keyGenParameterSpec);
            SecretKey secretKey = keyGenerator.generateKey();

            // 在 KeyStore 中保存的秘钥是无法拿到它的具体值的，通过如下方式获取只会返回 null
            // byte[] keyData = secretKey.getEncoded();

            return secretKey;
        } catch (Exception ex) {
            _textView1.setText(ex.toString());
            return null;
        }
    }

    // 加载 KeyStore 中的指定别名的 SecretKey
    @RequiresApi(api = Build.VERSION_CODES.M)
    private SecretKey loadSecretKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance(_keyStoreProvider);
            keyStore.load(null);

            if (!keyStore.containsAlias(_keyAlias)) {
                return null;
            }

            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry)keyStore.getEntry(_keyAlias, null);
            SecretKey secretKey = secretKeyEntry.getSecretKey();

            // 在 KeyStore 中保存的秘钥是无法拿到它的具体值的，通过如下方式获取只会返回 null
            // byte[] keyData = secretKey.getEncoded();

            return secretKey;
        } catch (Exception ex) {
            _textView1.setText(ex.toString());
            return null;
        }
    }
}