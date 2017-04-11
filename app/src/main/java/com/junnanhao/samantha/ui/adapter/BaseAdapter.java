package com.junnanhao.samantha.ui.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.ramotion.foldingcell.FoldingCell;

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
        protected Context context;

        private Boolean isToggleable = null;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public abstract void bindData(InfoBean bean);

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

        protected  int findIdByResName(String name, String defType) {
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
