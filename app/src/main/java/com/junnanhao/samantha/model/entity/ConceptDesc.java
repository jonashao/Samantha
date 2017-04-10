package com.junnanhao.samantha.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/9.
 * Describe concept in
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class ConceptDesc extends RealmObject {
    @PrimaryKey private int id;
    private Concept concept;
    /**
     * pattern to match this concept value
     */
    private String formatter;
    /**
     * regroup matched result into concept value
     */
    private String cather = "$1";

    /**
     * res Type and resName are used to find correspond view to show the concept,
     * mainly used in Ticket View
     */
    private String resType = "id";
    private String resName;

    /**
     * view type decide what kind of view to show the concept.
     *
     * textView : setText
     * imageView: setDrawableResource
     * metaInfo: infoView
     */
    private String viewType = "textView";
}
