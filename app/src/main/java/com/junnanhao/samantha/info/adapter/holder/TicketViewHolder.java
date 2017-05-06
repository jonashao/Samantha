package com.junnanhao.samantha.info.adapter.holder;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.model.entity.concept.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.info.utils.Setter;


import static com.junnanhao.samanthaviews.R.layout.train_ticket_card;

/**
 * Created by Jonas on 2017/4/11.
 * hold train ticket views
 */

public class TicketViewHolder extends InfoBeanViewHolder {
    public TicketViewHolder(View itemView) {
        super(itemView);
    }

    @NonNull
    @Override
    protected ImmutableMap<String, Object> getViews() {
        return ImmutableMap.<String, Object>builder().build();
    }

    @NonNull
    @Override
    protected ImmutableMap<Long, Setter> getSetters() {
        return ImmutableMap.<Long, Setter>builder().build();
    }

    public void bindData(InfoBean bean) {

        TextView tvSetting = (TextView) findViewByResName(bean.type().ui().resNameEdit(), "id");
        if (tvSetting != null) {
            tvSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        if (bean.type().id() == 1) {
            tvDetailTitle.setText("火车票");
        }

        for (ConceptDesc description : bean.type().conceptDescs()) {
            String resName = description.identifier();
            String defType = description.resType();
            if (resName != null) {
                TextView tv = (TextView) findViewByResName(resName, defType);
                String value = bean.valueOfConcept(description.concept());
                if (tv != null && value != null) {
                    tv.setText(value);
                } else {
                    if (tv != null) {
                        tv.setVisibility(View.INVISIBLE);
                    }
                    if (tvSetting != null) {
                        tvSetting.setVisibility(View.VISIBLE);
                    }
                }
                if (description.concept().id() == 1) {
                    tvDetailTitleContent.setText(value);
                }
            }
        }
    }

    @Override
    public void clear() {

    }
}
