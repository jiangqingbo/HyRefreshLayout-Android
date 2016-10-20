package com.huyunit.refreshlayout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: bobo
 * create time: 2016/10/18 15:25
 * Email: jqbo84@163.com
 */
public class SwipeRefreshRecyclerLayout extends SwipeRefreshLayout {
    //RecyclerView实例
    private RecyclerView mRecyclerView;
    // 上拉接口监听器, 到了最底部的上拉加载操作
    private OnLoadingListener mOnLoadingListener;
    // 是否在加载中 ( 上拉加载更多 )
    private boolean isLoading = false;

    public SwipeRefreshRecyclerLayout(Context context) {
        super(context);
    }

    public SwipeRefreshRecyclerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mRecyclerView == null) {
            getRecyclerView();
        }
    }

    // 获取RecyclerView对象
    private void getRecyclerView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof RecyclerView) {
                mRecyclerView = (RecyclerView) childView;
                // 设置滚动监听器给ListView
                mRecyclerView.addOnScrollListener(new MyOnScrollListener());
            }
        }
    }

    // 设置加载状态,添加或者移除加载更多圆形进度条
    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    //设置监听器
    public void setOnLoadingListener(OnLoadingListener loadingListener) {
        mOnLoadingListener = loadingListener;
    }

    private class MyOnScrollListener extends OnScrollListener {

        int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mRecyclerView.getAdapter().getItemCount()) {
                // 首先设置加载状态
                setLoading(true);
                // 调用加载数据的方法
                mOnLoadingListener.onLoadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //最后一个可见的ITEM
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    }

    // 加载更多的接口
    public interface OnLoadingListener {
        public void onLoadMore();
    }

}
