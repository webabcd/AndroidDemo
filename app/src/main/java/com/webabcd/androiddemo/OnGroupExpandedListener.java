/**
 * 本程序首页的 ExpandableListView 分组展开监听器
 */

package com.webabcd.androiddemo;

public interface OnGroupExpandedListener {
    /**
     * 分组展开
     *
     * @param groupPosition 分组的位置
     */
    void onGroupExpanded(int groupPosition);
}
