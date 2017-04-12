package com.junnanhao.samantha.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.struct.MetaInfo;
import com.junnanhao.samantha.ui.adapter.holder.MetaInfoViewHolder;

import java.util.List;

/**
 * Created by Jonas on 2017/4/12.
 * adapter for recyclerView MataInfo
 */

class MetaInfoAdapter extends RecyclerView.Adapter<MetaInfoViewHolder> {
    private List<MetaInfo> data;

    MetaInfoAdapter(List<MetaInfo> data) {
        this.data = data;
    }

    @Override
    public MetaInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meta_info, parent, false);
                return new MetaInfoViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).resId();
    }

    @Override
    public void onBindViewHolder(MetaInfoViewHolder holder, int position) {
        MetaInfo value = data.get(position);
        if (value != null) {
            holder.icMetaInfo.setImageResource(value.resId());
            holder.tvMetaInfo.setText(value.value());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
