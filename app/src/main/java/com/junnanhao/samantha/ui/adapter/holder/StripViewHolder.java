package com.junnanhao.samantha.ui.adapter.holder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.ConceptDesc;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.Synonyms;
import com.junnanhao.samantha.model.struct.Key2;
import com.junnanhao.samantha.ui.adapter.BaseAdapter;
import com.junnanhao.samantha.ui.utils.Setter;
import com.junnanhao.samantha.ui.utils.Setters;
import com.junnanhao.samantha.ui.utils.SubViewSetter;
import com.junnanhao.samanthaviews.util.ColorUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.junnanhao.samantha.ui.utils.Setter.IMAGE_VIEW;
import static com.junnanhao.samantha.ui.utils.Setter.INFO_VIEW;
import static com.junnanhao.samantha.ui.utils.Setter.PROPERTY_IMAGE_RESOURCE;
import static com.junnanhao.samantha.ui.utils.Setter.PROPERTY_LOCATION;
import static com.junnanhao.samantha.ui.utils.Setter.PROPERTY_PHONE;
import static com.junnanhao.samantha.ui.utils.Setter.PROPERTY_SUBJECT;
import static com.junnanhao.samantha.ui.utils.Setter.PROPERTY_TEXT;
import static com.junnanhao.samantha.ui.utils.Setter.PROPERTY_TIME;
import static com.junnanhao.samantha.ui.utils.Setter.PROPERTY_TITLE;
import static com.junnanhao.samantha.ui.utils.Setter.TEXT_VIEW;
import static com.junnanhao.samantha.ui.utils.Setter.TYPE_META;


public class StripViewHolder extends BaseAdapter.ViewHolder {
    @BindView(R.id.layout_title) ConstraintLayout layoutTitle;
    @BindView(R.id.list_meta_info) LinearLayout metaInfoList;
    @BindView(R.id.layout_detail_container) ConstraintLayout detailContainer;
    @BindView(R.id.ic_preview) ImageView icon;

    @BindViews({R.id.tv_data, R.id.tv_data_main, R.id.tv_data_second})
    List<TextView> tvsData;

    @BindViews({R.id.tv_title, R.id.tv_title_main, R.id.tv_subtitle})
    List<TextView> tvsTitle;

    @BindView(R.id.tv_detail_title) TextView tvDetailTitle;

    final private SubViewSetter titlesSetter = new SubViewSetter(tvsTitle);
    final private SubViewSetter dataSetter = new SubViewSetter(tvsData);

    private final Setter<SubViewSetter, String> TITLE_SETTER = new Setter<SubViewSetter, String>() {
        @Override
        public void set(SubViewSetter object, String value) {
            object.set(new SubViewSetter.Value(true, value));
        }
    };

    private final Setter<SubViewSetter, String> LOCATION_SETTER = new Setter<SubViewSetter, String>() {
        @Override
        public void set(SubViewSetter object, String value) {
            object.set(new SubViewSetter.Value(false, value));
        }
    };

    private final Setter<SubViewSetter, String> TIME_SETTER = new Setter<SubViewSetter, String>() {
        @Override
        public void set(SubViewSetter object, String value) {
            object.set(new SubViewSetter.Value(true, value));
        }
    };

    private final Setter<TextView, String> SUBJECT_SETTER = new Setter<TextView, String>() {
        @Override
        public void set(TextView object, String value) {
            object.setText(value);
            Realm realm = Realm.getDefaultInstance();
            Synonyms synonyms = realm.where(Synonyms.class)
                    .contains("candidates", value)
                    .findFirst();

            if (synonyms != null) {
                int drawableId = findIdByResName("ic_" + synonyms.identifier(), "drawable");
                icon.setImageResource(drawableId);

                int colorId = findIdByResName(synonyms.identifier(), "color");
                layoutPreview.setBackgroundResource(colorId);
                layoutTitle.setBackgroundResource(colorId);

                if (ColorUtils.isColorDark(context.getResources().getColor(colorId))) {
                    ButterKnife.apply(tvsData, TEXT_COLOR_SETTER, Color.WHITE);
                }
            }
        }
    };


    // use identifier to match view
    private ImmutableMap<String, Object> views = ImmutableMap.<String, Object>builder()
            .put("tv_subject", tvDetailTitle)
            .put("tv_title", titlesSetter)
            .put("tv_data", dataSetter)
            .put("tv_data_second", dataSetter)
            .build();

    // use view type and property to match setter
    private ImmutableMap<Key2, Setter> setters = ImmutableMap.<Key2, Setter>builder()
            .put(new Key2(TEXT_VIEW, PROPERTY_SUBJECT), SUBJECT_SETTER)
            .put(new Key2(INFO_VIEW, PROPERTY_TITLE), TITLE_SETTER)
            .put(new Key2(TYPE_META, PROPERTY_LOCATION), LOCATION_SETTER)
//            .put(new Key2(TYPE_META, PROPERTY_PHONE),)
            .put(new Key2(INFO_VIEW, PROPERTY_TIME), TIME_SETTER)
            .put(new Key2(TEXT_VIEW, PROPERTY_TEXT), Setters.TEXT_VIEW_TEXT)
            .put(new Key2(IMAGE_VIEW, PROPERTY_IMAGE_RESOURCE), Setters.IMAGE_VIEW_RESOURCE_ID)
            .build();


    public StripViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public void bindData(InfoBean bean) {
        for (ConceptDesc desc : bean.type().conceptDescs()) {

            String identifier = desc.identifier();
            Object view = views.get(identifier);

            String viewType = desc.viewType();
            String property = desc.property();
            Setter setter = setters.get(new Key2(viewType, property));

            String value = bean.valueOfConcept(desc.concept());

            if (setter != null && view != null)
                setter.set(view, value);
        }
    }


    private static final ButterKnife.Setter<TextView, Integer> TEXT_COLOR_SETTER = new ButterKnife.Setter<TextView, Integer>() {
        @Override
        public void set(@NonNull TextView view, Integer value, int index) {
            view.setTextColor(value);
        }
    };


}