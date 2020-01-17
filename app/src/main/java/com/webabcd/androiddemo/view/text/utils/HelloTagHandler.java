/**
 * 实现 Html.TagHandler 接口，用于解析并呈现自定义标签（本例是 <hello /> 标签）
 *
 * 本类的使用请参见 view/text/TextViewDemo4.java
 */

package com.webabcd.androiddemo.view.text.utils;

import android.text.Editable;
import android.text.Html;

import org.xml.sax.XMLReader;

public class HelloTagHandler implements Html.TagHandler {
    // 需要解析的自定义标签的名字
    private static final String TAG_HELLO = "hello";
    // 被自定义标签包裹的内容的起始位置
    private int _tagCotentStartIndex = 0;
    // 被自定义标签包裹的内容的结束位置
    private int _tagContentEndIndex = 0;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals(TAG_HELLO)) {
            if (opening) { // 内容开始
                tagCotentStart(tag, output, xmlReader);
            } else { // 内容结束
                tagContentEnd(tag, output, xmlReader);
            }
        }
    }

    // 标签包裹的内容开始
    private void tagCotentStart(String tag, Editable output, XMLReader xmlReader) {
        _tagCotentStartIndex = output.length();
    }

    // 标签包裹的内容结束
    private void tagContentEnd(String tag, Editable output, XMLReader xmlReader) {
        _tagContentEndIndex = output.length();

        // 在 <hello /> 标签包裹的内容前加上“hello: ”
        output.insert(_tagCotentStartIndex, "hello: ");
    }
}