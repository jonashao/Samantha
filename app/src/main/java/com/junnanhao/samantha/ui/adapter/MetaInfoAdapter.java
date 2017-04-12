package com.junnanhao.samantha.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.junnanhao.samanthaviews.MetaInfo;

import java.util.List;

/**
 * Created by Jonas on 2017/4/12.
 * adapter for recyclerView MataInfo
 */

public class MetaInfoAdapter extends RecyclerView.Adapter<MetaInfoAdapter.ViewHolder> {
    List<MetaInfo> data;

    public MetaInfoAdapter(List<MetaInfo> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
