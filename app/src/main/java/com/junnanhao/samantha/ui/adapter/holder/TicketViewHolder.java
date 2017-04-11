package com.junnanhao.samantha.ui.adapter.holder;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.junnanhao.samantha.model.entity.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.ui.adapter.BaseAdapter.ViewHolder;


import static com.junnanhao.samanthaviews.R.layout.train_ticket_card;

/**
 * Created by Jonas on 2017/4/11.
 * hold train ticket views
 */

public class TicketViewHolder extends ViewHolder {
    public TicketViewHolder(View itemView) {
        super(itemView);
        LayoutInflater.from(context).inflate(train_ticket_card, layoutPreview, true);
    }

    @Override
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
}