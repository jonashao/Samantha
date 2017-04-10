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
import com.junnanhao.samantha.model.entity.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.Synonyms;
import com.junnanhao.samanthaviews.InfoView;
import com.junnanhao.samanthaviews.MetaInfo;
import com.ramotion.foldingcell.FoldingCell;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

import static com.junnanhao.samanthaviews.R.layout.train_ticket_card;


public class RecyclerViewAdapter extends BaseAdapter<InfoBean, RecyclerViewAdapter.ViewHolder> {
    public RecyclerViewAdapter(OrderedRealmCollection<InfoBean> data) {
        super(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InfoView view = new InfoView(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final InfoBean bean = getItem(position);
        holder.bindData(bean);
    }

    static class ViewHolder extends BaseAdapter.ViewHolder {

        @BindView(R.id.cell) FoldingCell cell;
        @BindView(R.id.detail) LinearLayout menus;
        @BindView(R.id.surface) ConstraintLayout surface;

        private InfoView infoView;

        ViewHolder(InfoView itemView) {
            super(itemView);
            infoView = itemView;
        }

        private int findIdByResName(String name, String defType) {
            int id = 0;
            if (name != null) {
                id = context.getResources().getIdentifier(name, defType, context.getPackageName());
            }
            return id;
        }

        private View findViewByResName(String name, String defType) {
            int id = findIdByResName(name, defType);
            if (id != 0) {
                return ButterKnife.findById(cell, id);
            } else return null;
        }

        void bindData(InfoBean bean) {
            switch (bean.type().id()) {
                case 1: {
                    // ticket
                    LayoutInflater inflater = LayoutInflater.from(context);
                    surface.removeAllViews();
                    inflater.inflate(train_ticket_card, surface);
                    bindTicketData(bean);
                    break;
                }
                case 2: {
                    // logistics
                    bindLogisticsData(bean);
                    break;
                }

            }
        }

        void bindLogisticsData(InfoBean bean) {
            for (ConceptDesc desc : bean.type().conceptDescs()) {
                String value = bean.valueOfConcept(desc.concept());
                if (value == null) {
                    continue;
                }
                switch ((int) desc.concept().id()) {
                    case 12: // logistics company
                        Realm realm = Realm.getDefaultInstance();
                        Synonyms synonyms = realm.where(Synonyms.class)
                                .contains("candidates", value)
                                .findFirst();
                        if (synonyms != null) {
                            int drawableId = findIdByResName("ic_" + synonyms.identifier(), "drawable");
                            infoView.setIcon(drawableId);
                            int colorId = findIdByResName(synonyms.identifier(), "color");
                            infoView.setCardBackground(colorId);
                        }
                        infoView.setDetailTitle(value);
                        break;
                    case 13: // time
                        infoView.setContentMain(value);
                        break;
                    case 14: // place
                        infoView.setContentSecond(value);
                    default:
                        infoView.addMetaInfo(new MetaInfo(desc.viewType(), value));
                }
            }
        }

        void bindTicketData(InfoBean bean) {
            TextView tvSetting = (TextView) findViewByResName(bean.type().ui().resNameEdit(), "id");
            if (tvSetting != null) {
                tvSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            if (bean.type().id() == 1) {
                infoView.setTitle("火车票");
            }

            for (ConceptDesc description : bean.type().conceptDescs()) {
                String resName = description.resName();
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
                        infoView.setContentMain(value);
                    }
                }
            }
//            infoView.addDetailView(surface);
        }


        @OnClick(R.id.swipe)
        void toggle() {
            cell.toggle(false);
        }
    }
}
