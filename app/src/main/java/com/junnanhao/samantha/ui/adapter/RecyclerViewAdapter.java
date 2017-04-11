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

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.ui.adapter.holder.StripViewHolder;
import com.junnanhao.samantha.ui.adapter.holder.TicketViewHolder;


import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;

import static com.junnanhao.samanthaviews.R.layout.preview_strip;


public class RecyclerViewAdapter extends BaseAdapter<InfoBean, BaseAdapter.ViewHolder> {

    public RecyclerViewAdapter(OrderedRealmCollection<InfoBean> data) {
        super(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_info, parent, false);
        ConstraintLayout layoutPreview = ButterKnife.findById(view, R.id.preview);

        switch (viewType) {
            case 1: // train ticket
                viewHolder = new TicketViewHolder(view);
                break;
            case 2:
                inflater.inflate(preview_strip, layoutPreview, true);
                viewHolder = new StripViewHolder(view);
                break;
            default:
                viewHolder = new StripViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getData() != null) {
            InfoBean bean = getData().get(position);
            if (bean != null) {
                holder.bindData(getData().get(position));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getData() != null ? getData().get(position).type().id() : 0;
    }


}
