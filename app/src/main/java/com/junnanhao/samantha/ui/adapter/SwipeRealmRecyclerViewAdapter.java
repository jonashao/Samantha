package com.junnanhao.samantha.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import com.junnanhao.samantha.R;

import java.util.List;

import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Jonas on 2017/3/31.
 * Adapter to bind data to view.
 */

abstract class SwipeRealmRecyclerViewAdapter<T extends RealmModel, VH extends SwipeRealmRecyclerViewAdapter.ViewHolder>
        extends RealmRecyclerViewAdapter<T, VH>
        implements SwipeItemMangerInterface, SwipeAdapterInterface {

    private SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    SwipeRealmRecyclerViewAdapter(OrderedRealmCollection<T> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        protected Context context;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }
    }
}
