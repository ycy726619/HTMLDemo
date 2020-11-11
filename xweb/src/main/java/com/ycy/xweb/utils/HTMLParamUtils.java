package com.ycy.xweb.utils;

import com.ycy.xweb.constant.Constants;

/**
 * 2020/11/11
 * ycy
 */
public class HTMLParamUtils {

    public static String assembleParams(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            if (i == 0 || i == params.length - 1) {
                stringBuilder.append(params[i]);
            } else {
                stringBuilder.append(Constants.SPLIT_CONTENT).append(params[i]);
            }
        }
        return stringBuilder.toString();
    }
}
