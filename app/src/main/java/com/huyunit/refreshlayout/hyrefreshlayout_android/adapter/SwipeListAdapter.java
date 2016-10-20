package com.huyunit.refreshlayout.hyrefreshlayout_android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huyunit.refreshlayout.hyrefreshlayout_android.R;
import com.huyunit.refreshlayout.hyrefreshlayout_android.adapter.base.BaseListAdapter;
import com.huyunit.refreshlayout.hyrefreshlayout_android.adapter.base.ViewHolder;

import java.util.List;

public class SwipeListAdapter extends BaseListAdapter<String> {

    public SwipeListAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.f_list_item, null);
        }
        TextView textView = ViewHolder.get(convertView, R.id.tv_name);
        String bean = dataList.get(position);
        textView.setText(bean);
        return convertView;
    }

}