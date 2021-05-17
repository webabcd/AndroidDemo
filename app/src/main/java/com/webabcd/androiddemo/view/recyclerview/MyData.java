/**
 * 需要绑定的数据的实体类（用于 RecyclerView 的测试）
 */

package com.webabcd.androiddemo.view.recyclerview;

import com.webabcd.androiddemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyData {

    private int _logoId;
    private String _name;
    private String _comment;

    public MyData() {
    }

    public MyData(int logoId, String name, String comment) {
        this._logoId = logoId;
        this._name = name;
        this._comment = comment;
    }

    public int getLogoId() {
        return _logoId;
    }

    public String getName() {
        return _name;
    }

    public String getComment() {
        return _comment;
    }

    public void setLogoId(int logoId) {
        this._logoId = logoId;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setComment(String comment) {
        this._comment = comment;
    }

    public static List<MyData> generateDataList() {
        return generateDataList(0, 100);
    }

    public static List<MyData> generateDataList(int skip, int take) {
        List<MyData> myDataList = new ArrayList<>();
        Random random = new Random();
        for (int i = skip; i < skip + take; i++) {
            int randomLength = random.nextInt(100);
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < randomLength; x++) {
                stringBuilder.append(getRandomString(3));
            }
            myDataList.add(new MyData(R.drawable.img_sample_son, "n " + i, "comment " + stringBuilder.toString()));
        }
        return myDataList;
    }

    private static String getRandomString(int length) {
        Random random = new Random();
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
