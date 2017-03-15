package com.huyunit.refreshlayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huyunit.refreshlayout.R;

import java.util.List;

/**
 * author: bobo
 * create time: 2016/10/18 16:35
 * Email: jqbo84@163.com
 */
public abstract class BaseRefreshRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<T> mDatas;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;

    //上拉加载更多状态-默认为0
    private MoreStatusEnum mLoadMoreStatus = MoreStatusEnum.PULLUP_LOAD_MORE;

    public BaseRefreshRecyclerViewAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindData(holder, position);
    }

    /**
     * 填充item数据
     *
     * @param parent
     * @param viewType
     */
    public abstract RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType);

    /**
     * 填充item数据
     *
     * @param holder
     * @param position
     */
    public abstract void bindData(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        //RecyclerView的count设置为数据总条数+ 1（footerView）
        return mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {
            //最后一个item设置为footerView
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView loadText;
        private LinearLayout loadLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            loadText = (TextView) itemView.findViewById(R.id.tvLoadText);
            loadLayout = (LinearLayout) itemView.findViewById(R.id.loadLayout);
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        public TextView getLoadText() {
            return loadText;
        }

        public LinearLayout getLoadLayout() {
            return loadLayout;
        }
    }


    public void AddHeaderItem(List<T> items) {
        if (items != null) {
            mDatas.addAll(0, items);
            notifyItemRangeInserted(0, items.size());
        }
    }

    public void AddFooterItem(List<T> items) {
        if (items != null) {
            mDatas.addAll(mDatas.size(), items);
            notifyItemRangeInserted(mDatas.size(), items.size());
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param items
     */
    public void setData(List<T> items) {
        if (items != null) {
            mDatas = items;
        } else {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setLoadMoreStatus(MoreStatusEnum mLoadMoreStatus) {
        this.mLoadMoreStatus = mLoadMoreStatus;
    }

    public MoreStatusEnum getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    /**
     * 更新加载更多状态
     *
     * @param status
     */
    public void changeMoreStatus(MoreStatusEnum status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }

    public enum MoreStatusEnum {
        PULLUP_LOAD_MORE,   //上拉加载更多
        LOADING_MORE,       //正在加载中
        NO_LOAD_MORE        //没有加载更多 隐藏
    }
}

