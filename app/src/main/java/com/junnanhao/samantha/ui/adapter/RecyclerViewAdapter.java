package com.junnanhao.samantha.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.entity.InfoBean;
import com.junnanhao.samantha.ui.custom.TrainTicketView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jonas on 2017/3/31.
 * Adapter to bind data to view.
 */

public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.ViewHolder> {

    private List<InfoBean> infoBeans;

    public RecyclerViewAdapter(List<InfoBean> infoBeans) {

        this.infoBeans = infoBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        InfoBean bean = infoBeans.get(position);
        viewHolder.setData(bean);
    }

    @Override
    public int getItemCount() {
        return infoBeans.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe) SwipeLayout swipeLayout;
        @BindView(R.id.menu_wrapper) LinearLayout menus;
        @BindView(R.id.surface) CardView surface;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(InfoBean bean) {
            surface.addView(new TrainTicketView(itemView.getContext()));

        }
    }
}
