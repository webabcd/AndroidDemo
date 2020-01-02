/**
 * 自定义 BaseExpandableListAdapter，用于为本程序首页的 ExpandableListView 提供数据和模板
 */

package com.webabcd.androiddemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

public class MainExpandableListAdapter extends BaseExpandableListAdapter {
    private final String LOG_TAG = "MainAdapter";

    private List<MainNavigationBean> mNavigationBeanList;
    private OnGroupExpandedListener mOnGroupExpandedListener;

    public MainExpandableListAdapter(List<MainNavigationBean> navigationBeanList) {
        mNavigationBeanList = navigationBeanList;
    }

    public void setOnGroupExpandedListener(OnGroupExpandedListener onGroupExpandedListener) {
        mOnGroupExpandedListener = onGroupExpandedListener;
    }

    @Override
    public int getGroupCount() {
        return mNavigationBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mNavigationBeanList.get(groupPosition).getNodeList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mNavigationBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mNavigationBeanList.get(groupPosition).getNodeList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * 10000 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.textViewTitle.setText(mNavigationBeanList.get(groupPosition).getTitle());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.textViewTitle.setText(mNavigationBeanList.get(groupPosition).getNodeList().get(childPosition).getTitle());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Log.d(LOG_TAG, "onGroupExpanded() " + groupPosition);
        if (mOnGroupExpandedListener != null) {
            mOnGroupExpandedListener.onGroupExpanded(groupPosition);
        }
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        Log.d(LOG_TAG, "onGroupCollapsed() " + groupPosition);
    }

    private static class GroupViewHolder {
        TextView textViewTitle;
    }

    private static class ChildViewHolder {
        TextView textViewTitle;
    }
}
