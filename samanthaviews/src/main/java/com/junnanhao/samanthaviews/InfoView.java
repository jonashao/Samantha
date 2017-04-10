package com.junnanhao.samanthaviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by Jonas on 2017/3/31.
 * train ticket
 */

public class InfoView extends LinearLayout {
    @BindView(R2.id.surface) ConstraintLayout surface;
    @BindView(R2.id.layout_title) ConstraintLayout layoutTitle;
    @BindView(R2.id.list_meta_info) LinearLayout metaInfoList;

    @Nullable
    @BindViews({R2.id.ic_preview})
    List<AppCompatImageView> icons;


    @BindViews({R2.id.tv_data, R2.id.tv_data_main, R2.id.tv_data_second})
    List<TextView> tvs;
    private short data_status = 0b00;
    private String[] data_texts = new String[3];


    public InfoView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.content_card, this);
        ButterKnife.bind(this);

        setGravity(Gravity.CENTER);
    }

    public void setIcon(@DrawableRes int resId) {
        if (icons != null) {
            ButterKnife.apply(icons, ICON_SOURCE_SETTER, resId);
        }
    }


    public void setTitle(String title) {

    }

    public void setCardBackground(@ColorRes int resId) {
        surface.setBackgroundResource(resId);
        layoutTitle.setBackgroundResource(resId);

        if (ColorUtils.isColorDark(getResources().getColor(resId))) {
            setTextColor(Color.WHITE);
        }
    }

    public void setSubTitle(String subTitle) {


    }

    public void setContentMain(String content) {
        data_texts[1] = content;
        showContents(tvs, (short) 0b01, data_texts);
    }

    public void setContentSecond(String content) {
        data_texts[2] = content;
        showContents(tvs, (short) 0b10, data_texts);
    }

    public void addMetaInfo(MetaInfo meta) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        switch (meta.type()) {
            case MetaInfo.TYPE_PHONE:
                ConstraintLayout metaView = (ConstraintLayout) inflater.inflate(R.layout.content_meta_info, metaInfoList, false);
                ImageView ic = ButterKnife.findById(metaView, R.id.ic_meta_info);
                ic.setImageResource(R.drawable.ic_phone_black_24dp);
                TextView tv = ButterKnife.findById(metaView, R.id.tv_meta_info);
                tv.setText(meta.value());
                metaInfoList.addView(metaView);
                metaView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "he", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    public void addAction(Action action) {
    }

    public void setTextColor(int color) {
        ButterKnife.apply(tvs, TEXT_COLOR, color);
    }

    static final ButterKnife.Setter<TextView, Integer> TEXT_COLOR = new ButterKnife.Setter<TextView, Integer>() {
        @Override
        public void set(@NonNull TextView view, Integer value, int index) {
            view.setTextColor(value);
        }
    };

    static final ButterKnife.Setter<ImageView, Integer> ICON_SOURCE_SETTER = new ButterKnife.Setter<ImageView, Integer>() {
        @Override
        public void set(@NonNull ImageView view, Integer value, int index) {
            view.setImageResource(value);
        }
    };

    public void setText(String resName, String s) {

    }

    private void showContents(List<TextView> tvs, short which, String[] values) {
        data_status |= which;
        if (data_status == 3) {
            tvs.get(which).setText(values[which]);
            tvs.get(which).setVisibility(VISIBLE);
            tvs.get(0).setVisibility(INVISIBLE);
        } else {
            tvs.get(0).setText(values[which]);
            tvs.get(0).setVisibility(VISIBLE);
            tvs.get(1).setVisibility(INVISIBLE);
            tvs.get(2).setVisibility(INVISIBLE);
        }
    }


}
