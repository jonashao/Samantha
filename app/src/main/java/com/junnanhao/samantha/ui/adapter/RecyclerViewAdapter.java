/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.junnanhao.samantha.ui.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.ActionMenuItem;
import com.junnanhao.samantha.model.entity.Concept;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.ramotion.foldingcell.FoldingCell;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.OrderedRealmCollection;


public class RecyclerViewAdapter extends BaseAdapter<InfoBean, RecyclerViewAdapter.ViewHolder> {
    public RecyclerViewAdapter(OrderedRealmCollection<InfoBean> data) {
        super(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(com.junnanhao.samanthaviews.R.layout.content_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final InfoBean bean = getItem(position);
        holder.bindData(bean);
    }

    static class ViewHolder extends BaseAdapter.ViewHolder {

        @BindView(R.id.cell) FoldingCell cell;
        @BindView(R.id.menu_wrapper) LinearLayout menus;
        @BindView(R.id.surface) ConstraintLayout surface;

        ViewHolder(View itemView) {
            super(itemView);
        }

        private TextView findViewByResName(String resName) {
            if (resName == null) {
                return null;
            }
            int id = context.getResources().getIdentifier(resName, "id", context.getPackageName());
            if (id != 0) {
                return ButterKnife.findById(surface, id);
            } else return null;
        }

        void bindData(InfoBean bean) {
            surface.removeAllViews();
            LayoutInflater.from(context).inflate(com.junnanhao.samanthaviews.R.layout.train_ticket_card, surface);

            TextView tvSetting = findViewByResName(bean.type().resNameEdit());
            if (tvSetting != null) {
                tvSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            for (Concept concept : bean.type().concepts()) {
                String resName = concept.resIdName();
                if (resName != null) {
                    TextView tv = findViewByResName(resName);
                    String value = bean.valueOfConcept(concept);
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
                }
            }

            for (ActionMenuItem item : bean.actions()) {
                addMenu(item);
            }
        }

        void addMenu(ActionMenuItem item) {
            TextView tv = (TextView) LayoutInflater.from(context)
                    .inflate(R.layout.item_swipe_menu, menus, false);
            tv.setText(item.title());
            menus.addView(tv);
        }

        @OnClick(R.id.swipe)
        void toggle(){
            cell.toggle(false);
        }
    }
}
