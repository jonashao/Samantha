package com.junnanhao.samantha.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.ui.utils.Setter;
import com.junnanhao.samantha.model.struct.MetaInfo;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Jonas on 2017/3/31.
 * Adapter to bind data to view.
 */

public abstract class BaseAdapter<T extends RealmModel, VH extends BaseAdapter.ViewHolder>
        extends RealmRecyclerViewAdapter<T, VH> {

    public BaseAdapter(OrderedRealmCollection<T> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

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
