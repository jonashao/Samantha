package com.junnanhao.samantha.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.Concept;
import com.junnanhao.samantha.model.entity.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.struct.Key2;
import com.junnanhao.samantha.ui.utils.Setter;
import com.junnanhao.samanthaviews.MetaInfo;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

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

    BaseAdapter(OrderedRealmCollection<T> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail) LinearLayout layoutDetail;
        @BindView(R.id.preview) protected ConstraintLayout layoutPreview;
        @BindView(R.id.cell) FoldingCell cell;
        @BindView(R.id.tv_detail_title) protected TextView tvDetailTitle;
        @BindView(R.id.tv_detail_title_content) protected TextView tvDetailTitleContent;
        @BindView(R.id.list_meta_info) RecyclerView rvMetaInfo;
        protected Context context;
        protected MetaInfoAdapter metaInfoAdapter;
        protected List<MetaInfo> mataInfoList;

        // use identifier to match view
        protected ImmutableMap<String, Object> views;

        // use view type and property to match setter
        protected ImmutableMap<Long, Setter> setters;

        private Boolean isToggleable = null;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
            setupRvMetaInfo();
        }

        private void setupRvMetaInfo() {
            mataInfoList = new ArrayList<>();
            metaInfoAdapter = new MetaInfoAdapter(mataInfoList);
            rvMetaInfo.setAdapter(metaInfoAdapter);
        }

        @NonNull
        protected abstract ImmutableMap<String, Object> getViews();

        @NonNull
        protected abstract ImmutableMap<Long, Setter> getSetters();

        @SuppressWarnings("unchecked")
        public void bindData(InfoBean bean) {
            for (ConceptDesc desc : bean.type().conceptDescs()) {

                String identifier = desc.identifier();
                Object view = getViews().get(identifier);
                Setter setter = getSetters().get(desc.concept().id());
                String value = bean.valueOfConcept(desc.concept());

                if (setter != null && view != null && value != null)
                    setter.set(view, value);
            }
        }

        @OnClick(R.id.cell)
        void foldSwitch() {
            if (isToggleable == null) {
                int detailHeight = layoutDetail.getHeight();
                int previewHeight = layoutPreview.getHeight();
                int p = (previewHeight << 1 - detailHeight);
                isToggleable = (p <= 0);
            }
            if (isToggleable) {
                cell.toggle(false);
            }
        }

        protected int findIdByResName(String name, String defType) {
            int id = 0;
            if (name != null) {
                id = context.getResources().getIdentifier(name, defType, context.getPackageName());
            }
            return id;
        }

        protected View findViewByResName(String name, String defType) {
            int id = findIdByResName(name, defType);
            if (id != 0) {
                return ButterKnife.findById(cell, id);
            } else return null;
        }

    }
}
