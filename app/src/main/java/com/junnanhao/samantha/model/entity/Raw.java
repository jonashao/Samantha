package com.junnanhao.samantha.model.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/1/5.
 * Raw information
 */

@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@Data
public class Raw extends RealmObject {
    public static final String TYPE_SMS = "sms";

    @PrimaryKey
    private long id;
    private String category;
    /**
     * Type of source, like email and sms
     */
    private String type;
    private String sender;
    private String subject;
    private String body;
    private Date datetime;
    private boolean seen;

    private boolean isHandled = false;

}
