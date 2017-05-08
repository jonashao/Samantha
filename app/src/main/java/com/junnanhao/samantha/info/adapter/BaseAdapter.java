package com.junnanhao.samantha.info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import lombok.Setter;

/**
 * Created by Jonas on 2017/3/31.
 * Adapter to bind data to view.
 */

public abstract class BaseAdapter<T extends RealmModel, VH extends BaseAdapter.ViewHolder>
        extends RealmBasedRecyclerViewAdapter<T, VH> {

    public BaseAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }


    public static abstract class ViewHolder extends RealmViewHolder {

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
