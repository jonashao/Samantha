package com.junnanhao.samantha.info.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.junnanhao.samantha.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MetaInfoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ic_meta_info) public ImageView icMetaInfo;
    @BindView(R.id.tv_meta_info) public TextView tvMetaInfo;

    public MetaInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}