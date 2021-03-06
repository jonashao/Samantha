package com.junnanhao.samantha.info.adapter.holder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.Synonyms;
import com.junnanhao.samantha.info.utils.Setter;
import com.junnanhao.samantha.info.utils.SubViewSetter;
import com.junnanhao.samantha.model.entity.concept.ConceptValue;
import com.junnanhao.samanthaviews.util.ColorUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import io.realm.Realm;


public class StripViewHolder extends InfoBeanViewHolder {

    @BindViews({R.id.tv_data, R.id.tv_data_main, R.id.tv_data_second})
    List<TextView> tvsData;
    @BindViews({R.id.tv_title, R.id.tv_title_main, R.id.tv_subtitle})
    List<TextView> tvsTitle;

    @BindView(R.id.ic_preview)
    ImageView icon;

    private String typeIdentifier;

    final private SubViewSetter dataSetter = new SubViewSetter(tvsData);

    public void bindData(InfoBean bean) {
        typeIdentifier = bean.type().name();
        super.bindData(bean);
        String title = bean.valueOfUi("title_main");
        String subtitle = bean.valueOfUi("subtitle");
        String data = bean.valueOfUi("data");
        String dataSecond = bean.valueOfUi("data_second");
        SubViewSetter dataSetter = new SubViewSetter(tvsData);
        dataSetter.set(new SubViewSetter.Value(true, data));
        dataSetter.set(new SubViewSetter.Value(false, dataSecond));
        SubViewSetter titleSetter = new SubViewSetter(tvsTitle);
        titleSetter.set(new SubViewSetter.Value(true, title));
        titleSetter.set(new SubViewSetter.Value(false, subtitle));
        String identifier = bean.valueOfUi("identifier");
        Realm realm = Realm.getDefaultInstance();
        Synonyms synonyms = realm.where(Synonyms.class)
                .contains(Synonyms.FILED_CANDIDATES, identifier)
                .findFirst();
        if (synonyms != null) {
            setColorIcon(synonyms.identifier());
        } else {
            setColorIcon(typeIdentifier);
        }
        realm.close();
        List<ConceptValue> conceptValues = bean.valuesOfUi("meta");
        for (ConceptValue conceptValue : conceptValues) {
            addMeta(conceptValue);
        }
    }

    private static final Setter<StripViewHolder, String> LOCATION_SETTER = new Setter<StripViewHolder, String>() {
        @Override
        public void set(StripViewHolder holder, String value) {
            holder.dataSetter.set(new SubViewSetter.Value(false, value));
            holder.addMeta("location", value);
        }
    };

    private static final Setter<StripViewHolder, String> TIME_SETTER = new Setter<StripViewHolder, String>() {
        @Override
        public void set(StripViewHolder holder, String value) {
            holder.dataSetter.set(new SubViewSetter.Value(true, value));
            holder.tvDetailTitleContent.setText(value);
        }
    };

    private final Setter<String, String> META_SETTER = new Setter<String, String>() {
        @Override
        public void set(String object, String value) {
            addMeta(object, value);
        }
    };


    private static final Setter<StripViewHolder, String> SUBJECT_SETTER = new Setter<StripViewHolder, String>() {
        @Override
        public void set(StripViewHolder holder, String value) {
            holder.tvDetailTitle.setText(value);
            Realm realm = Realm.getDefaultInstance();
            Synonyms synonyms = realm.where(Synonyms.class)
                    .contains(Synonyms.FILED_CANDIDATES, value)
                    .findFirst();

            if (synonyms != null) {
                holder.setColorIcon(synonyms.identifier());
            } else {
                holder.setColorIcon(holder.typeIdentifier);
            }
        }
    };


    private void setColorIcon(String identifier) {
        int drawableId = findIdByResName("ic_" + identifier, "drawable");
        icon.setImageResource(drawableId);

        int colorId = findIdByResName(identifier, "color");
        int color = ContextCompat.getColor(context, colorId);
        layoutPreview.setBackgroundResource(colorId);
        layoutTitle.setBackgroundResource(colorId);
        cell.initialize(1000, ColorUtils.darken(color, .7f), 2);

        if (colorId != 0 && ColorUtils.isColorDark(context.getResources().getColor(colorId))) {
            ButterKnife.apply(tvsData, TEXT_COLOR_SETTER, Color.WHITE);
        }
    }


    public StripViewHolder(View itemView) {
        super(itemView);
    }

    @NonNull
    @Override
    protected ImmutableMap<String, Object> getViews() {
        if (views == null) {
            views = ImmutableMap.<String, Object>builder()
                    .put("subject", StripViewHolder.this)
                    .put("time", StripViewHolder.this)
                    .put("location", StripViewHolder.this)
                    .put("phone", "phone")
                    .put("pick_number", "pick_number")
                    .build();
        }
        return views;
    }

    @NonNull
    @Override
    protected ImmutableMap<Long, Setter> getSetters() {
        if (setters == null) {
            setters = ImmutableMap.<Long, Setter>builder()
                    .put(12L, SUBJECT_SETTER)
                    .put(14L, LOCATION_SETTER)
                    .put(13L, TIME_SETTER)
                    .put(11L, META_SETTER)
                    .put(15L, META_SETTER)
                    .build();
        }
        return setters;
    }


    @Override
    public void clear() {
        ButterKnife.apply(tvsData, TEXT_SETTER, "");
    }


    private static final ButterKnife.Setter<TextView, Integer> TEXT_COLOR_SETTER = new ButterKnife.Setter<TextView, Integer>() {
        @Override
        public void set(@NonNull TextView view, Integer value, int index) {
            view.setTextColor(value);
        }
    };

    private static final ButterKnife.Setter<TextView, String> TEXT_SETTER = new ButterKnife.Setter<TextView, String>() {
        @Override
        public void set(@NonNull TextView view, String value, int index) {
            view.setText(value);
        }
    };


}