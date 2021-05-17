/**
 * 自定义 RecyclerView.Adapter
 */

package com.webabcd.androiddemo.view.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.webabcd.androiddemo.R;

import java.util.List;

public class RecyclerViewDemo4Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<MyData> _myDataList;

    // 是否还有更多数据可供加载
    private boolean _hasMoreItems = true;

    public RecyclerViewDemo4Adapter(List<MyData> myDataList) {
        _myDataList = myDataList;
    }

    // 追加数据
    public void appendData(List<MyData> myDataList) {
        _myDataList.addAll(myDataList);
    }

    // 告诉我是否还有更多数据可供加载
    public void setHasMoreItems(boolean hasMoreItems) {
        this._hasMoreItems = hasMoreItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            // 用于显示数据的 item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recyclerview_myrecyclerviewadapter_1, parent, false);
            return new ViewHolder(view);
        } else {
            // 用于显示“加载更多”的 item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recyclerview_recyclerviewdemo8_loadmore, parent, false);
            return new LoadMoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ViewHolder) { // 用于显示数据的 item
            ViewHolder holder = (ViewHolder)viewHolder;
            MyData myData = _myDataList.get(position);
            holder.imgLogo.setImageResource(myData.getLogoId());
            holder.txtName.setText(myData.getName());
            holder.txtComment.setText(myData.getComment());
        } else if (viewHolder instanceof LoadMoreViewHolder) { // 用于显示“加载更多”的 item
            LoadMoreViewHolder holder = (LoadMoreViewHolder)viewHolder;
            if (!_hasMoreItems) { // 没有更多数据时
                holder.textView.setText("没有更多数据了");
                holder.progressBar.setVisibility(View.GONE);
            } else { // 请求更多数据时
                holder.textView.setText("正在加载");
                holder.progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        // + 1 是因为多了一个“加载更多”的 item
        return _myDataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < _myDataList.size()) {
            return 0; // 用于显示数据的 item
        } else {
            return 1; // 用于显示“加载更多”的 item
        }
    }


    // 用于显示数据的 item 的 ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {

        private View container;
        private ImageView imgLogo;
        private TextView txtName;
        private TextView txtComment;

        public ViewHolder (View view)  {
            super(view);

            container = view;
            imgLogo = view.findViewById(R.id.imgLogo);
            txtName = view.findViewById(R.id.txtName);
            txtComment = view.findViewById(R.id.txtComment);
        }
    }

    // 用于显示“加载更多”的 item 的 ViewHolder
    class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        private View container;
        private ProgressBar progressBar;
        private TextView textView;

        public LoadMoreViewHolder(View view) {
            super(view);

            container = view;
            progressBar = view.findViewById(R.id.progressBar);
            textView = view.findViewById(R.id.textView);
        }
    }
}
