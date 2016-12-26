package com.huyunit.refreshlayout.hyrefreshlayout_android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.huyunit.refreshlayout.adapter.BaseRefreshRecyclerViewAdapter;
import com.huyunit.refreshlayout.SwipeRefreshRecyclerLayout;
import com.huyunit.refreshlayout.hyrefreshlayout_android.R;
import com.huyunit.refreshlayout.hyrefreshlayout_android.adapter.SwipeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author: bobo
 * create time: 2016/10/18 14:53
 * Email: jqbo84@163.com
 */
public class SwipeRefreshRecyclerLayoutFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshRecyclerLayout.OnLoadingListener{

    private RecyclerView recyclerView;
    private SwipeRefreshRecyclerLayout swipeRefreshRecyclerLayout;

    private List<String> sList = new ArrayList<>();
    private SwipeRecyclerViewAdapter swipeRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static SwipeRefreshRecyclerLayoutFragment newInstance() {
        SwipeRefreshRecyclerLayoutFragment fragment = new SwipeRefreshRecyclerLayoutFragment();
        return fragment;
    }

    @Override
    protected void initView(LayoutInflater inflater, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.f_swiperefreshlayout_recyclerview);
        recyclerView = getViewById(R.id.recyclerView);
        swipeRefreshRecyclerLayout = getViewById(R.id.swipeRefreshLayout);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        swipeRefreshRecyclerLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);

        for (int i = 0; i < 10; i++) {

            sList.add(" Item "+i);
        }

        initRecylerView();
        initListener();
    }

    private void initRecylerView() {
        swipeRecyclerViewAdapter = new SwipeRecyclerViewAdapter(getContext(),sList);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        //添加动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //添加分割线
//        recyclerView.addItemDecoration(new RefreshItemDecoration(this,RefreshItemDecoration.VERTICAL_LIST));

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(swipeRecyclerViewAdapter);
    }

    private void initListener() {
        swipeRefreshRecyclerLayout.setOnRefreshListener(this);
        swipeRefreshRecyclerLayout.setOnLoadingListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> headDatas = new ArrayList<String>();
                for (int i = 20; i <30 ; i++) {

                    headDatas.add("Heard Item "+i);
                }
                swipeRecyclerViewAdapter.AddHeaderItem(headDatas);

                //刷新完成
                swipeRefreshRecyclerLayout.setRefreshing(false);
                Toast.makeText(getContext(), "更新了 "+headDatas.size()+" 条目数据", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }

    @Override
    public void onLoadMore() {
        //设置正在加载更多
        swipeRecyclerViewAdapter.changeMoreStatus(BaseRefreshRecyclerViewAdapter.MoreStatusEnum.LOADING_MORE);
        //改为网络请求
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //
                List<String> footerDatas = new ArrayList<String>();
                for (int i = 0; i< 10; i++) {

                    footerDatas.add("footer  item" + i);
                }
                swipeRecyclerViewAdapter.AddFooterItem(footerDatas);
                //设置回到上拉加载更多
                swipeRecyclerViewAdapter.changeMoreStatus(BaseRefreshRecyclerViewAdapter.MoreStatusEnum.PULLUP_LOAD_MORE);
                //没有加载更多了
                //mRefreshAdapter.changeMoreStatus(mRefreshAdapter.NO_LOAD_MORE);
                Toast.makeText(getContext(), "更新了 "+footerDatas.size()+" 条目数据", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }

}
