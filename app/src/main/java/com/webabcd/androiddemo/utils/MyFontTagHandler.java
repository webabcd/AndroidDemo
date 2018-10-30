/**
 * 实现 Html.TagHandler 接口，用于解析并呈现自定义标签（本例是 <myfont /> 标签），同时处理该自定义标签的自定义属性（本例处理了 <myfont /> 标签的 color 属性和 size 属性）
 */

package com.webabcd.androiddemo.utils;

import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.HashMap;

public class MyFontTagHandler implements Html.TagHandler {
    // 需要解析的自定义标签的名字
    private static final String TAG_MYFONT = "myfont";

    // 被自定义标签包裹的内容的起始位置
    private int _tagContentStartIndex = 0;
    // 被自定义标签包裹的内容的结束位置
    private int tagContentEndIndex = 0;

    // 自定义标签的属性 key/value 集合
    private HashMap<String, String> _tagAttributes = new HashMap<String, String>();

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if(tag.equalsIgnoreCase(TAG_MYFONT)){
            // 加载 <myfont /> 标签的全部属性
            loadTagAttributes(xmlReader);

            if(opening){ // 内容开始
                tagContentStart(tag, output, xmlReader);
            }else{ // 内容结束
                tagContentEnd(tag, output, xmlReader);
            }
        }
    }

    // 标签包裹的内容开始
    private void tagContentStart(String tag, Editable output, XMLReader xmlReader) {
        _tagContentStartIndex = output.length();
    }

    // 标签包裹的内容结束
    private void tagContentEnd(String tag, Editable output, XMLReader xmlReader){
        tagContentEndIndex = output.length();

        String color = _tagAttributes.get("color");
        String size = _tagAttributes.get("size");
        size = size.split("px")[0];

        // 处理 color 属性
        if(!TextUtils.isEmpty(color)){
            output.setSpan(new ForegroundColorSpan(Color.parseColor(color)), _tagContentStartIndex, tagContentEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // 处理 size 属性
        if(!TextUtils.isEmpty(size)){
            output.setSpan(new AbsoluteSizeSpan(Integer.parseInt(size)), _tagContentStartIndex, tagContentEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    // 加载指定 xml 标签中的全部属性到 _tagAttributes 变量中
    private void loadTagAttributes(final XMLReader xmlReader) {
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[])dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer)lengthField.get(atts);

            for(int i = 0; i < len; i++){
                _tagAttributes.put(data[i * 5 + 1], data[i * 5 + 4]);
            }
        }
        catch (Exception e) {

        }
    }
}