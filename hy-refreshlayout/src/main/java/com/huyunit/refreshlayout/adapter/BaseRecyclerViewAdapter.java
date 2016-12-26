package com.huyunit.refreshlayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * author: bobo
 * create time: 2016/12/26 14:10
 * Email: jqbo84@163.com
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<T> dataList;

    public BaseRecyclerViewAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dataList = dataList;
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
     * 创建ViewHolder对象
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
        return dataList.size();
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void clear(){
        dataList.clear();
        notifyDataSetChanged();
    }
}
