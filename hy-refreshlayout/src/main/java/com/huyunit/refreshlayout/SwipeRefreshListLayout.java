package com.huyunit.refreshlayout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能
 * author: bobo
 * create time: 2016/10/19 18:07
 * Email: jqbo84@163.com
 */
public class SwipeRefreshListLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    // listview实例
    private ListView mListView;
    // 上拉接口监听器, 到了最底部的上拉加载操作
    private OnLoadingListener mOnLoadingListener;
    // ListView的加载中footer
    private View mListViewFooter;
    // 是否在加载中 ( 上拉加载更多 )
    private boolean isLoading = false;

    public SwipeRefreshListLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //一个圆形进度条
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.refresh_footer_view, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    // 获取ListView对象
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView
                mListView.setOnScrollListener(this);
            }
        }
    }

    // 设置加载状态,添加或者移除加载更多圆形进度条
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListView.addFooterView(mListViewFooter);
        } else {
            mListView.removeFooterView(mListViewFooter);
        }
    }

    //设置监听器
    public void setOnLoadingListener(OnLoadingListener loadingListener) {
        mOnLoadingListener = loadingListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 判断是否到了最底部，并且不是在加载数据的状态
        if (mListView.getLastVisiblePosition() == mListView.getAdapter()
                .getCount() - 1 && isLoading == false) {
            // 首先设置加载状态
            setLoading(true);
            // 调用加载数据的方法
            mOnLoadingListener.onLoadMore();

        }

    }

    // 加载更多的接口
    public interface OnLoadingListener {
        public void onLoadMore();
    }
}
