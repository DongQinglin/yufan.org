package me.dongqinglin.flina.rest.data.Helper;

import me.dongqinglin.flina.rest.data.internal.Constant;

public class TextHelper {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals(Constant.EMPTY_STR);
    }

    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }
}
