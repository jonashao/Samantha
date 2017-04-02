package com.junnanhao.samantha.model.struct;

import com.junnanhao.samantha.workflow.scanner.Scanner;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * Created by Jonas on 2017/4/2.
 * encapsulated information source
 */

@Accessors(fluent = true)
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC, suppressConstructorProperties = true)
public class Source {
    @NonNull private Scanner scanner;
    private String[] permissions;
}
