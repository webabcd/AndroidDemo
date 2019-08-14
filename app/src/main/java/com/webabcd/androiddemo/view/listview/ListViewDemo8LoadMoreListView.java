/**
 * 自定义一个 ListView 用于支持滚动到底部加载更多数据
 */

package com.webabcd.androiddemo.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.webabcd.androiddemo.R;

public class ListViewDemo8LoadMoreListView extends ListView implements AbsListView.OnScrollListener {
    // 用于显示“正在加载框”的 view
    private View _rootView;
    // “正在加载框”的 loading
    private ProgressBar _progressBar;
    // “正在加载框”的文字提示
    private TextView _textView;

    // 是否还有更多数据可供加载
    private boolean _hasMoreItems = true;
    // 是否正在加载数据
    private boolean _isLoading = false;

    public ListViewDemo8LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        _rootView = LayoutInflater.from(context).inflate(R.layout.item_view_listview_listviewdemo8_loadmore, null);
        _progressBar = (ProgressBar) _rootView.findViewById(R.id.progressBar);
        _textView = (TextView) _rootView.findViewById(R.id.textView);

        // 将“正在加载框”添加到 ListView 的 footer 中
        super.addFooterView(_rootView, null, false);

        // 监听 ListView 的滚动事件
        super.setOnScrollListener(this);
    }

    // 外部调用，用于告诉 ListView 是否还有更多数据可供加载
    public void setHasMoreItems(boolean hasMoreItems) {
        this._hasMoreItems = hasMoreItems;

        if (!hasMoreItems) {
            _textView.setText("没有更多数据了");
            _progressBar.setVisibility(View.GONE);
        } else {
            _textView.setText("正在加载");
            _progressBar.setVisibility(View.VISIBLE);
        }
    }

    // 外部调用，用于告诉 ListView 当前数据是否已经加载完成
    public void loadComplete() {
        _isLoading = false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() == view.getCount() - 1 && !_isLoading && mOnLoadMoreListener != null && _hasMoreItems) {
                _isLoading = true;
                // 触发需要加载更多数据的事件
                performLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    // ListView 需要获取更多数据时的事件
    private OnLoadMoreListener mOnLoadMoreListener;
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }
    protected void performLoadMore() {
        if (mOnLoadMoreListener != null)
            mOnLoadMoreListener.onLoadMore();
    }
}
