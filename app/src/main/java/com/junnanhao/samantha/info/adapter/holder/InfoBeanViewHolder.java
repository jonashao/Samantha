package com.junnanhao.samantha.info.adapter.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.concept.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.struct.MetaInfo;
import com.junnanhao.samantha.info.adapter.BaseAdapter;
import com.junnanhao.samantha.info.adapter.MetaInfoAdapter;
import com.junnanhao.samantha.info.utils.Setter;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class InfoBeanViewHolder extends BaseAdapter.ViewHolder {
    @BindView(R.id.cell)  FoldingCell cell;
    @BindView(R.id.detail)  LinearLayout layoutDetail;
    @BindView(R.id.preview) ConstraintLayout layoutPreview;
    @BindView(R.id.tv_detail_title) TextView tvDetailTitle;
    @BindView(R.id.tv_detail_title_content) TextView tvDetailTitleContent;
    @BindView(R.id.list_meta_info)  RecyclerView rvMetaInfo;
    protected Context context;
    private MetaInfoAdapter metaInfoAdapter;
    private List<MetaInfo> metaInfoList;

    // use identifier to match view
    ImmutableMap<String, Object> views;

    // use view type and property to match setter
    ImmutableMap<Long, Setter> setters;

    private Boolean isToggleable = null;

    InfoBeanViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
        setupRvMetaInfo();
    }

    private void setupRvMetaInfo() {
        metaInfoList = new ArrayList<>();
        metaInfoAdapter = new MetaInfoAdapter(metaInfoList);
        rvMetaInfo.setAdapter(metaInfoAdapter);
        rvMetaInfo.setLayoutManager(new LinearLayoutManager(context));
    }

    void addMeta(String type, String value) {
        MetaInfo meta = new MetaInfo(type, value);
        if (!metaInfoList.contains(meta)) {
            metaInfoList.add(meta);
        }
    }

    @NonNull
    protected abstract ImmutableMap<String, Object> getViews();

    @NonNull
    protected abstract ImmutableMap<Long, Setter> getSetters();


    @SuppressWarnings("unchecked")
    public void bindData(InfoBean bean) {
        this.metaInfoList.clear();

        for (ConceptDesc desc : bean.type().conceptDescs()) {

            String identifier = desc.identifier();
            Object view = getViews().get(identifier);
            Setter setter = getSetters().get(desc.concept().id());
            String value = bean.valueOfConcept(desc.concept());

            if (setter != null && view != null && value != null)
                setter.set(view, value);
        }
        metaInfoAdapter.notifyDataSetChanged();

        if (isToggleable == null) {
            int detailHeight = layoutDetail.getHeight();
            int previewHeight = layoutPreview.getHeight();
            int p = (previewHeight << 1 - detailHeight);
            isToggleable = (p <= 0);
        }
    }

//    @OnClick(R.id.cell)
    public void foldSwitch() {
        if (isToggleable != null && isToggleable) {
            cell.toggle(false);
        }
    }


    View findViewByResName(String name, String defType) {
        int id = findIdByResName(name, defType);
        if (id != 0) {
            return ButterKnife.findById(cell, id);
        } else return null;
    }
}