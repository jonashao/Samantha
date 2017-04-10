package com.junnanhao.samantha.workflow.scanner;

import com.junnanhao.samantha.model.entity.Raw;

import java.util.List;

/**
 * Created by Jonas on 2017/4/2.
 * Scan information from sources
 */

public interface Scanner {
    /**
     * This is an io operation
     * @return a list of raw information scanned from source
     */
    List<Raw> scan();
}
