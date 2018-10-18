package com.webabcd.androiddemo;

import java.util.List;

public class MainNavigationBean {


    /**
     * title : 基础
     * node : [{"title":"android 基础示例","url":"com.webabcd.androiddemo.basic.demo"}]
     */

    private String title;
    private List<NodeBean> node;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NodeBean> getNode() {
        return node;
    }

    public void setNode(List<NodeBean> node) {
        this.node = node;
    }

    public static class NodeBean {
        /**
         * title : android 基础示例
         * url : com.webabcd.androiddemo.basic.demo
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
