package com.huyunit.refreshlayout.hyrefreshlayout_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huyunit.refreshlayout.adapter.RecyclerViewAdapter;
import com.huyunit.refreshlayout.hyrefreshlayout_android.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: bobo
 * create time: 2016/10/18 19:30
 * Email: jqbo84@163.com
 */
public class SwipeRecyclerViewAdapter extends RecyclerViewAdapter<String> {

    public SwipeRecyclerViewAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = getInflater().inflate(R.layout.item_refresh_recylerview, parent, false);

            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            View itemView = getInflater().inflate(R.layout.refresh_footer_view, parent, false);

            return new FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void bindData(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String str = getDatas().get(position);
            itemViewHolder.mTvContent.setText(str);
        } else if (holder.getItemViewType() == TYPE_FOOTER) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (getLoadMoreStatus()) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.getLoadText().setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footerViewHolder.getLoadText().setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:
                    //隐藏加载更多
                    footerViewHolder.getLoadLayout().setVisibility(View.GONE);
                    break;
            }
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_value)
        TextView mTvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initListener(itemView);
        }

        private void initListener(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "poistion " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
