package com.junnanhao.samantha.info.adapter.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.concept.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.concept.ConceptValue;
import com.junnanhao.samantha.model.struct.MetaInfo;
import com.junnanhao.samantha.info.adapter.BaseAdapter;
import com.junnanhao.samantha.info.adapter.MetaInfoAdapter;
import com.junnanhao.samantha.info.utils.Setter;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import timber.log.Timber;

public abstract class InfoBeanViewHolder extends BaseAdapter.ViewHolder {
    @BindView(R.id.cell) FoldingCell cell;
    @BindView(R.id.detail) LinearLayout layoutDetail;
    @BindView(R.id.surface) FrameLayout layoutPreview;
    @BindView(R.id.tv_detail_title) TextView tvDetailTitle;
    @BindView(R.id.tv_detail_title_content) TextView tvDetailTitleContent;
    @BindView(R.id.list_meta_info) RecyclerView rvMetaInfo;
    @BindView(R.id.layout_title) ConstraintLayout layoutTitle;
    @BindView(R.id.layout_detail_container) ConstraintLayout detailContainer;
    @BindView(R.id.tv_origin_text) TextView tvOriginText;
    @BindView(R.id.layout_detail_content) LinearLayout layoutDetailContent;

    protected Context context;
    private MetaInfoAdapter metaInfoAdapter;
    private List<MetaInfo> metaInfoList;
    private InfoBean bean;
    private int mShortAnimationDuration;

    // use identifier to match view
    ImmutableMap<String, Object> views;

    // use view type and property to match setter
    ImmutableMap<Long, Setter> setters;

    private Boolean isToggleable = null;
    private boolean isShowingOriginText = false;

    InfoBeanViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
        setupRvMetaInfo();
        tvOriginText.setVisibility(View.GONE);
        mShortAnimationDuration = context.getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    private void setupRvMetaInfo() {
        metaInfoList = new ArrayList<>();
        metaInfoAdapter = new MetaInfoAdapter(metaInfoList);
        rvMetaInfo.setAdapter(metaInfoAdapter);
        rvMetaInfo.setLayoutManager(new LinearLayoutManager(context));
    }

    void addMeta(String type, String value) {
        MetaInfo meta = new MetaInfo(type, value);
        if (!metaInfoList.contains(meta) && meta.value() != null && meta.value().length() > 0) {
            metaInfoList.add(meta);
        }
    }

    void addMeta(@Nonnull ConceptValue conceptValue) {
        MetaInfo meta = new MetaInfo(conceptValue);
        if (!metaInfoList.contains(meta) && meta.value() != null && meta.value().length() > 0) {
            metaInfoList.add(meta);
        }
    }

    @NonNull
    protected abstract ImmutableMap<String, Object> getViews();

    @NonNull
    protected abstract ImmutableMap<Long, Setter> getSetters();

    @SuppressWarnings("unchecked")
    public void bindData(InfoBean bean) {
        this.bean = bean;
        this.metaInfoList.clear();
        tvDetailTitle.setText(bean.valueOfUi("title"));
        tvOriginText.setText(bean.raw().body());

        for (ConceptDesc desc : bean.type().conceptDescs()) {

            String identifier = desc.identifier();
            Object view = getViews().get(identifier);
            Setter setter = getSetters().get(desc.concept().id());
            String value = bean.valueOfConcept(desc.concept());

            if (setter != null && view != null && value != null)
                setter.set(view, value);
        }
        metaInfoAdapter.notifyDataSetChanged();
        if (metaInfoList.size() >= 2) {
            isToggleable = true;
        }
    }


    @SuppressWarnings("unused")
    private void checkToggleable() {
        int detailHeight = layoutDetail.getHeight();
        int previewHeight = 0;
        if (layoutPreview != null) {
            previewHeight = layoutPreview.getHeight();
        }
        Timber.d("title:%s, detail:%s", previewHeight, detailHeight);
        int p = (previewHeight << 1 - detailHeight);
        isToggleable = (p <= 0);
    }

    @OnClick({R.id.cell, R.id.layout_title})
    void foldSwitch() {
        if (isShowingOriginText) {
            crossFading(tvDetailTitle, layoutDetailContent, mShortAnimationDuration);
            isShowingOriginText = false;
            return;
        }

        if (isToggleable != null && isToggleable) {
            tvOriginText.setVisibility(View.GONE);
            cell.toggle(false);
        }
    }

    @OnLongClick(R.id.layout_title)
    boolean showOriginText() {
        crossFading(layoutDetailContent, tvOriginText, mShortAnimationDuration);
        isShowingOriginText = true;
        return true;
    }

    private void crossFading(final View from, View to, int duration) {
        to.setAlpha(0f);
        to.setVisibility(View.VISIBLE);
        to.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);

        from.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        from.setVisibility(View.GONE);
                    }
                });
    }

    View findViewByResName(String name, String defType) {
        int id = findIdByResName(name, defType);
        if (id != 0) {
            return ButterKnife.findById(cell, id);
        } else return null;
    }

    public abstract void clear();


}