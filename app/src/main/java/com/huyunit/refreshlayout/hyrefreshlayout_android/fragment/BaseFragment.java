package com.huyunit.refreshlayout.hyrefreshlayout_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huyunit.refreshlayout.hyrefreshlayout_android.App;
import com.huyunit.refreshlayout.hyrefreshlayout_android.rest.RestClient;

/**
 * author: bobo
 * create time: 2016/10/18 14:45
 * Email: jqbo84@163.com
 */
public abstract class BaseFragment extends Fragment {
    protected App mApp;
    protected View mContentView;
    protected RestClient client;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mApp = App.getInstance();
        client = mApp.getClient();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            initView(inflater, savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void setContentView(LayoutInflater inflater, @LayoutRes int layoutResID) {
        mContentView = inflater.inflate(layoutResID, null);
    }

    /**
     * 初始化View控件
     */
    protected abstract void initView(LayoutInflater inflater, Bundle savedInstanceState);


    /**
     * 初始化View控件
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mContentView.findViewById(id);
    }
}
