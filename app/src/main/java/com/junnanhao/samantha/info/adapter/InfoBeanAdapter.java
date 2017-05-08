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

package com.junnanhao.samantha.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.info.adapter.holder.StripViewHolder;
import com.junnanhao.samantha.info.adapter.holder.TicketViewHolder;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.info.adapter.holder.InfoBeanViewHolder;

import butterknife.ButterKnife;
import io.realm.RealmResults;

import static com.junnanhao.samantha.R.layout.preview_strip;
import static com.junnanhao.samantha.R.layout.train_ticket_card;


public class InfoBeanAdapter extends BaseAdapter<InfoBean, InfoBeanViewHolder> {

    public InfoBeanAdapter(Context context, final RealmResults<InfoBean> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public InfoBeanViewHolder onCreateRealmViewHolder(ViewGroup parent, int viewType) {
        InfoBeanViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_info, parent, false);
        FrameLayout container = ButterKnife.findById(view, R.id.surface);
        switch (viewType) {
            case 1: // train ticket
                inflater.inflate(train_ticket_card, container, true);
                viewHolder = new TicketViewHolder(view);
                break;
            default:
                inflater.inflate(preview_strip, container, true);
                viewHolder = new StripViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindRealmViewHolder(InfoBeanViewHolder holder, int position) {
        if (realmResults != null) {
            InfoBean bean = realmResults.get(position);
            if (bean != null) {
                holder.bindData(bean);
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        return realmResults != null ? realmResults.get(position).type().id() : 0;
    }


}
