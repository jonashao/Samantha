package com.junnanhao.samanthaviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by Jonas on 2017/3/31.
 * train ticket
 */

public class InfoView extends LinearLayout {

    @BindView(R2.id.surface) ConstraintLayout surface;
    @Nullable
    @BindView(R2.id.ic_preview)
    ImageView icon;

    public InfoView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.content_card, this);
        ButterKnife.bind(this);

        inflater.inflate(R.layout.preview_strip, surface);
        setGravity(Gravity.CENTER);
    }

    public void setIcon(@DrawableRes int resId) {
        if (icon != null)
            icon.setImageResource(resId);
    }

    public void setIcon(Drawable drawable) {
//        icon.setImageDrawable(drawable);
    }

    public void setTitle(String title) {

    }

    public void setSubTitle(String subTitle) {

    }

    public void setPreviewContent(String previewContent) {
    }

    public void setPreviewContentSecondary(String previewContentSecondary) {
    }

    public void addMetaInfo(MetaInfo meta) {
    }

    public void addAction(Action action) {
    }

}
