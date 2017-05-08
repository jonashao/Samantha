package com.junnanhao.samantha.info.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.struct.MetaInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jonas on 2017/4/12.
 * adapter for recyclerView MataInfo
 */
public class MetaInfoAdapter extends RecyclerView.Adapter<MetaInfoAdapter.MetaInfoViewHolder> {
    private List<MetaInfo> data;

    public MetaInfoAdapter(List<MetaInfo> data) {
        this.data = data;
    }

    @Override
    public MetaInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meta_info, parent, false);
                return new MetaInfoViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).resId();
    }

    @Override
    public void onBindViewHolder(final MetaInfoViewHolder holder, int position) {
        final MetaInfo value = data.get(position);
        if (value != null) {
            holder.icMetaInfo.setImageResource(value.resId());
            holder.tvMetaInfo.setText(value.value());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (value.type()) {
                        case "phone":
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + value.value()));
                            holder.itemView.getContext().startActivity(intent);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MetaInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ic_meta_info) ImageView icMetaInfo;
        @BindView(R.id.tv_meta_info) TextView tvMetaInfo;

        MetaInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
