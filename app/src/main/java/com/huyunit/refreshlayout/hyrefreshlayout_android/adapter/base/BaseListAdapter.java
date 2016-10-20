package com.huyunit.refreshlayout.hyrefreshlayout_android.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ListAdapter自定义的抽象类集成基类的实现方式
 * 
 * @author: bobo Email: jqbo84@163.com 
 * create date: 2014-12-23 上午11:29:05
 * 
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

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
	public List<T> dataList = new ArrayList<T>();
	
	public BaseListAdapter(Context context, List<T> dataList) {
		super();
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		return dataList == null ? 0 : dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList == null || dataList.size()<=0 ? null :dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return dataList.size() != 0 ? position : -1;
	}

}
