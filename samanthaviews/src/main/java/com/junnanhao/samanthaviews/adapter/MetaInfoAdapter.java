package com.junnanhao.samanthaviews.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.junnanhao.samanthaviews.MetaInfo;

import java.util.List;

/**
 * Created by Jonas on 2017/4/10.
 */

public class MetaInfoAdapter extends BaseAdapter {
    private List<MetaInfo> data;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MetaInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        return null;
    }


    private static class ViewHolder {

    }
}
