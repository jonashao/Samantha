package com.junnanhao.samantha.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Jonas on 2017/3/31.
 * Adapter to bind data to view.
 */

abstract class BaseAdapter<T extends RealmModel, VH extends BaseAdapter.ViewHolder>
        extends RealmRecyclerViewAdapter<T, VH>{

    BaseAdapter(OrderedRealmCollection<T> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    static class ViewHolder extends RecyclerView.ViewHolder {
        protected Context context;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }
    }
}
