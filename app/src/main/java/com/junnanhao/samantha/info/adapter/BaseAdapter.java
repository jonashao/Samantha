package com.junnanhao.samantha.info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Jonas on 2017/3/31.
 * Adapter to bind data to view.
 */

public abstract class BaseAdapter<T extends RealmObject, VH extends BaseAdapter.ViewHolder>
        extends RealmRecyclerViewAdapter<T, VH> {

    public BaseAdapter(OrderedRealmCollection<T> data) {
        super(data, false);
        setHasStableIds(true);
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    public void remove(final int position) {
        if (getData() == null) {
            return;
        }
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmObject t = getData().get(position);
                t.deleteFromRealm();
                notifyDataSetChanged();
            }
        });
    }


    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        protected Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        protected int findIdByResName(String name, String defType) {
            int id = 0;
            if (name != null) {
                id = context.getResources().getIdentifier(name, defType, context.getPackageName());
            }
            return id;
        }

    }
}
