package com.junnanhao.samantha.info;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junnanhao.samantha.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/5/5.
 */

public class StripView extends ConstraintLayout {
    @BindView(R.id.tv_title_main) TextView mainTitle;
    @BindView(R.id.tv_subtitle) TextView subtitle;
    @BindView(R.id.tv_title) TextView title;
    @BindView(R.id.tv_data) TextView data;

    public StripView(Context context) {
        super(context);
        init();
    }

    public StripView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutParams(new ActionBarOverlayLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        inflate(getContext(), R.layout.preview_strip, this);
        ButterKnife.bind(this);
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    @Accessors(fluent = true)
    @Setter
    public static class Builder {
        private Context context;
        private String title = "Title";
        private String subtitle;
        private String contentL = "Data";
        private String contentL2;
        private int themeColor;
        private String identifier;

        Builder(Context context) {
            this.context = context;
        }

        public StripView build() {
            StripView stripView = new StripView(context);
            stripView.title.setText(title);
            stripView.title.setVisibility(VISIBLE);
            stripView.data.setText(contentL);
            stripView.data.setVisibility(VISIBLE);
            return stripView;
        }
    }

}
