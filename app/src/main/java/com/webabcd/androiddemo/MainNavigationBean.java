/**
 * 本程序首页的 ExpandableListView 数据源的实体类
 */

package com.webabcd.androiddemo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainNavigationBean {
    @SerializedName("title")
    private String mTitle; // 父节点 title
    @SerializedName("node")
    private List<NodeBean> mNodeList; // 子节点列表

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public List<NodeBean> getNodeList() {
        return mNodeList;
    }

    public void setNodeList(List<NodeBean> nodeList) {
        this.mNodeList = nodeList;
    }



    // 注：可以安装一个名为 GsonFormat 的插件（安装后可通过快捷键 alt + s 调出），其用于将 json 字符串解析为实体类
    public static class NodeBean {
        @SerializedName("title")
        private String mTitle; // 子节点 title
        @SerializedName("url")
        private String mUrl; // 子节点 url

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            this.mUrl = url;
        }
    }
}
