package com.huyunit.refreshlayout.hyrefreshlayout_android.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.huyunit.refreshlayout.SwipeRefreshListLayout;
import com.huyunit.refreshlayout.hyrefreshlayout_android.R;
import com.huyunit.refreshlayout.hyrefreshlayout_android.adapter.SwipeListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author: bobo
 * create time: 2016/10/19 18:11
 * Email: jqbo84@163.com
 */
public class SwipeRefreshListLayoutFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshListLayout.OnLoadingListener, AdapterView.OnItemClickListener {

    private SwipeRefreshListLayout swipeRefreshListLayout;
    private ListView listView;
    private SwipeListAdapter swipeListAdapter;

    private List<String> sList = new ArrayList<>();

    public static SwipeRefreshListLayoutFragment newInstance() {
        SwipeRefreshListLayoutFragment fragment = new SwipeRefreshListLayoutFragment();
        return fragment;
    }

    @Override
    protected void initView(LayoutInflater inflater, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.f_swiperefreshlayout_listview);
        swipeRefreshListLayout = getViewById(R.id.swipeRefreshLayout);
        listView = getViewById(R.id.listview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        swipeRefreshListLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        initListAdapter();
        initListener();
    }

    public void initListAdapter() {
        for (int i = 3; i < 32; i++) {
            sList.add("SwipeRefreshListLayout_" + i);
        }
        swipeListAdapter = new SwipeListAdapter(getActivity(), sList);
        listView.setAdapter(swipeListAdapter);

        listView.setOnItemClickListener(this);
    }

    public void initListener() {
        swipeRefreshListLayout.setOnRefreshListener(this);
        swipeRefreshListLayout.setOnLoadingListener(this);
    }

    private void refresh() {
        swipeListAdapter.dataList = sList;
        swipeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        swipeRefreshListLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据
                sList.clear();
                for (int i = 0; i < 23; i++) {
                    sList.add("SwipeRefreshListLayout_" + i);
                }
                refresh();

                // 更新完后调用该方法结束刷新
                swipeRefreshListLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        swipeRefreshListLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据
                int size = sList.size();
                for (int i = size; i < size + 10; i++) {
                    sList.add("MyListSwipeRefreshLayout_Footer_" + i);
                }
                refresh();
                // 更新完后调用该方法结束刷新
                swipeRefreshListLayout.setLoading(false);
            }
        }, 1000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),
                "这是第" + String.valueOf(position + 1) + "项",
                Toast.LENGTH_SHORT).show();
    }
}
