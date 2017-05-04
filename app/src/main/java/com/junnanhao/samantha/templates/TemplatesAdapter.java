package com.junnanhao.samantha.templates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.template.TemplateItem;
import com.junnanhao.samantha.info.adapter.BaseAdapter;

import butterknife.BindView;
import io.realm.OrderedRealmCollection;
import timber.log.Timber;

/**
 * Created by Jonas on 2017/4/30.
 * Adapter for templates list
 */

 class TemplatesAdapter extends BaseAdapter<TemplateItem, TemplatesAdapter.ViewHolder> {

    TemplatesAdapter(OrderedRealmCollection<TemplateItem> data) {
        super(data);
        Timber.d("adapter size: %d",data.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getData() != null) {
            TemplateItem template = getData().get(position);
            if (template != null) {
                holder.title.setText(template.title());
                holder.version.setText(template.version());
                holder.description.setText(template.description());
            }
        }
    }

    static class ViewHolder extends BaseAdapter.ViewHolder {
        @BindView(R.id.img_template_avatar) ImageView avatar;
        @BindView(R.id.tv_template_title) TextView title;
        @BindView(R.id.tv_template_version) TextView version;
        @BindView(R.id.tv_template_description) TextView description;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
