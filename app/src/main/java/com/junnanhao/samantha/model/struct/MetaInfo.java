package com.junnanhao.samantha.model.struct;

import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.concept.Concept;
import com.junnanhao.samantha.model.entity.concept.ConceptValue;

import lombok.Getter;
import lombok.experimental.Accessors;


/**
 * Created by Jonas on 2017/4/6.
 * Encapsulated information type and value to represent in cell folder.
 */

@Getter
@Accessors(fluent = true)
public class MetaInfo {

    private String type;
    private String value;
    private Integer resId;

    public MetaInfo(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public MetaInfo(ConceptValue conceptValue) {
        Concept concept = conceptValue.concept();
        value = conceptValue.value();
        int resId;
        do {
            type = concept.meaning();
            resId = MetaInfo.typeResMap.get(type);
            concept = concept.parent();
        } while (resId == 0 && concept != null);
    }

    public static ImmutableMap<String, Integer> typeResMap = ImmutableMap.<String, Integer>builder()
            .put("phone", R.drawable.ic_phone_black_24dp)
            .put("location", R.drawable.ic_location_on_black_24dp)
            .put("place", R.drawable.ic_location_on_black_24dp)
            .build();


    public int resId() {
        if (resId == null) {
            resId = typeResMap.get(type);
            if (resId == null) {
                resId = R.drawable.ic_label_black_24dp;
            }
        }
        return resId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaInfo metaInfo = (MetaInfo) o;

        return type.equals(metaInfo.type) && value.equals(metaInfo.value);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
