package com.junnanhao.samantha.info;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.info.adapter.MetaInfoAdapter;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.struct.MetaInfo;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;

public class InfoView extends ConstraintLayout {
    @BindView(R.id.cell) FoldingCell cell;
    @BindView(R.id.detail) LinearLayout layoutDetail;

    ViewGroup layoutPreview;
    @BindView(R.id.tv_detail_title) TextView tvDetailTitle;
    @BindView(R.id.tv_detail_title_content) TextView tvDetailTitleContent;
    @BindView(R.id.list_meta_info) RecyclerView rvMetaInfo;
    private MetaInfoAdapter metaInfoAdapter;
    private List<MetaInfo> metaInfoList;
    private Boolean isToggleable = null;

    public InfoView(Context context) {
        super(context);
        init();
    }

    void init() {
        setLayoutParams(new ActionBarOverlayLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        inflate(getContext(), R.layout.item_info, this);
        ButterKnife.bind(this);
        setupRvMetaInfo();
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }


    @Accessors(fluent = true)
    @Setter
    public static class Builder {
        private Context context;
        private ViewGroup surface;


        Builder(Context context) {
            this.context = context;
        }

        public InfoView build() {
            InfoView infoView = new InfoView(context);
            if (surface != null) {
                if (infoView.layoutPreview != null) {
                    infoView.cell.removeView(infoView.layoutPreview);
                }
                infoView.layoutPreview = surface;
                infoView.cell.addView(surface);
            }

            return infoView;
        }
    }

    private void setupRvMetaInfo() {
        metaInfoList = new ArrayList<>();
        metaInfoAdapter = new MetaInfoAdapter(metaInfoList);
        rvMetaInfo.setAdapter(metaInfoAdapter);
        rvMetaInfo.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    void addMeta(String type, String value) {
        MetaInfo meta = new MetaInfo(type, value);
        if (!metaInfoList.contains(meta)) {
            metaInfoList.add(meta);
        }
    }


    @SuppressWarnings("unchecked")
    public void bindData(InfoBean bean) {
        this.metaInfoList.clear();

        metaInfoAdapter.notifyDataSetChanged();

        if (isToggleable == null) {
            int detailHeight = layoutDetail.getHeight();
            int previewHeight = layoutPreview.getHeight();
            int p = (previewHeight << 1 - detailHeight);
            isToggleable = (p <= 0);
        }
        isToggleable = false;
    }

    //    @OnClick(R.id.cell)
    public void foldSwitch() {
        if (isToggleable != null && isToggleable) {
            cell.toggle(false);
        }
    }


}