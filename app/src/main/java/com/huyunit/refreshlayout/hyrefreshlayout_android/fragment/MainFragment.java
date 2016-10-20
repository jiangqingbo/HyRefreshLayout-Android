package com.huyunit.refreshlayout.hyrefreshlayout_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.huyunit.refreshlayout.hyrefreshlayout_android.R;

/**
 * author: bobo
 * create time: 2016/10/18 19:08
 * Email: jqbo84@163.com
 */
public class MainFragment extends BaseFragment {

    private TextView textView;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    protected void initView(LayoutInflater inflater, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.f_main);
        textView = getViewById(R.id.textView);
        System.out.println("MainFragment.initView = " + textView.getText().toString());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        textView.setText(getString(R.string.app_name));
        System.out.println("MainFragment.initData");
    }
}
