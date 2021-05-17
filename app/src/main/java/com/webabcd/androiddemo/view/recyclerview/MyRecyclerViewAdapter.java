/**
 * 自定义 RecyclerView.Adapter
 *
 * 适配器中包含了数据和项模板
 */

package com.webabcd.androiddemo.view.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webabcd.androiddemo.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{
    private final String LOG_TAG = "RecyclerView.Adapter";

    private List<MyData> _myDataList;

    public MyRecyclerViewAdapter(List<MyData> myDataList) {
        _myDataList = myDataList;
    }

    public void updateData(List<MyData> myDataList) {
        _myDataList = myDataList;
    }

    // 创建自定义 ViewHolder 实例
    // 注：这里的返回类型 ViewHolder 是你在 RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> 中指定的泛型
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 根据不同的布局类别加载不同的项模板，这个布局类别是在 getItemViewType() 中计算的（表头和表尾也可以通过此特性实现，我就不写示例了）
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recyclerview_myrecyclerviewadapter_1, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recyclerview_myrecyclerviewadapter_2, parent, false);
        }
        // 构造自定义 ViewHolder
        ViewHolder holder = new ViewHolder(view);

        // item 中的 imgLogo 控件的点击事件
        holder.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Toast.makeText(view.getContext(), "image click: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // item 本身的点击事件
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Toast.makeText(view.getContext(), "item click: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // item 本身的长按事件
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = holder.getAdapterPosition();
                Toast.makeText(view.getContext(), "item long click: " + position, Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        return holder;
    }

    // 每次绘制 item 时都会调用 onBindViewHolder()，需要在此为 item 中的控件赋值
    // 注：这里的参数 ViewHolder 的类型是你在 RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> 中指定的泛型
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 多多上下滚动 RecyclerView 来了解一下调用 onBindViewHolder() 的时机
        Log.d(LOG_TAG, String.format("onBindViewHolder: %d", position));

        MyData myData = _myDataList.get(position);
        holder.imgLogo.setImageResource(myData.getLogoId());
        holder.txtName.setText(myData.getName());
        holder.txtComment.setText(myData.getComment());

        /*
        // 如果使用的是 StaggeredGridLayoutManager 布局，则可以通过如下方式合并排列方向上的单元格
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams)
        {
            StaggeredGridLayoutManager.LayoutParams sglmlp = (StaggeredGridLayoutManager.LayoutParams)lp;
            sglmlp.setFullSpan(true);
        }
        */
    }

    // 需要呈现的 item 的总数
    @Override
    public int getItemCount() {
        return _myDataList.size();
    }

    // 返回指定索引位置的 item 的布局类别，然后可以在 onCreateViewHolder() 中根据不同的布局类别加载不同的项模板（表头和表尾也可以通过此特性实现，我就不写示例了）
    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    // 自定义 RecyclerView.ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{

        private View container;
        private ImageView imgLogo;
        private TextView txtName;
        private TextView txtComment;

        public ViewHolder (View view)
        {
            super(view);

            container = view;
            imgLogo = view.findViewById(R.id.imgLogo);
            txtName = view.findViewById(R.id.txtName);
            txtComment = view.findViewById(R.id.txtComment);
        }

    }
}
