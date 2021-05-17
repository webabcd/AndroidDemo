/**
 * 自定义 RecyclerView.OnScrollListener
 */

package com.webabcd.androiddemo.view.recyclerview;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerViewDemo4OnScrollListener extends RecyclerView.OnScrollListener {

    // 最近一次滑动是否是上滑
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            // 获取最后一个完全显示的 item 的 position
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();

            // 判断是否滑动到了最后一个 item 并且是上滑
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                // 滚动到底了
                onBottom();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        // 大于 0 代表上滑
        isSlidingUpward = dy > 0;
    }

    /**
     * 滚动到底的回调
     */
    public abstract void onBottom();
}
