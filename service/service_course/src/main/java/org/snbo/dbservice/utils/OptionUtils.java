package org.snbo.dbservice.utils;

import org.springframework.stereotype.Component;

/**
 * @author sunbo
 * @date 2022-06-17-23:09
 */
@Component
public class OptionUtils {
    private static final String GRADE19 = "2019";
    private static final String GRADE20 = "2020";
    private static final String GRADE21 = "2021";
    private static final String GRADE22 = "2022";

    public static String getGRADE19() {
        return GRADE19;
    }

    public static String getGRADE20() {
        return GRADE20;
    }

    public static String getGRADE21() {
        return GRADE21;
    }

    public static String getGRADE22() {
        return GRADE22;
    }
}
