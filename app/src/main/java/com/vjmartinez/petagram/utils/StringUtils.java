package com.vjmartinez.petagram.utils;

import android.text.Editable;

public class StringUtils {

    /**
     * True if a String is null or empty
     * @param value
     * @return
     */
    public static boolean isEmpty(String value){
        return value == null || value.isEmpty();
    }

    /**
     *
     * @param value The editable
     * @return true if the text is empty
     */
    public static boolean isEmpty(Editable value){
        return value == null || value.toString().isEmpty();
    }

    public static String nvl(String value, String defaultValue){
        return nvl(value, defaultValue, false);
    }

    /**
     * Replace a String with a default value when string param is null or empty
     * @param value The original value
     * @param defaultValue The replace value
     * @param replaceEmtpy if true method replace even when string is empty
     * @return The new String
     */
    public static String nvl(String value, String defaultValue, boolean replaceEmtpy){
        if(value == null){
            return defaultValue;
        }

        if(replaceEmtpy && value.isEmpty()){
            return defaultValue;
        }

        return value;
    }
}
