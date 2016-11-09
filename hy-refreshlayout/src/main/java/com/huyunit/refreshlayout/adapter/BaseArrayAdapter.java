package com.huyunit.refreshlayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * ListAdapter自定义的抽象类集成基类的实现方式
 *
 * @author: bobo Email: jqbo84@163.com
 * create date: 2016-10-10 下午16:29:05
 */

public abstract class BaseArrayAdapter<T> extends BaseAdapter {

    /**
     * 上下文
     */
    protected Context context = null;
    /**
     * 用于具化XML布局
     */
    protected LayoutInflater mInflater = null;
    /**
     * 适配器的数据源
     */
    public T[] datas;

    public BaseArrayAdapter(Context context, T[] datas) {
        super();
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas == null || datas.length <= 0 ? null : datas[position];
    }

    @Override
    public long getItemId(int position) {
        return datas.length != 0 ? position : -1;
    }

}
