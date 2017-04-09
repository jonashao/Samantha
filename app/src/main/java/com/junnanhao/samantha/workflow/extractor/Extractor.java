package com.junnanhao.samantha.workflow.extractor;

import com.junnanhao.samantha.model.entity.InfoBean;


/**
 * Created by Jonas on 2017/4/9.
 * Extract Information bean from src
 */

public interface Extractor {
    InfoBean extract(String src);
}
