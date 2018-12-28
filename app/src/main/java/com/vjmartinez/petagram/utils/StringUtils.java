package com.vjmartinez.petagram.utils;

import android.text.Editable;

public class StringUtils {

    /**
     * Verify if an String object is or not empty
     * @param value The string to evaluate
     * @return  True if a String is null or empty
     */
    public static boolean isEmpty(String value){
        return value == null || value.isEmpty();
    }

    /**
     * Verify if an String object is or not empty
     * @param value The editable
     * @return true if the text is empty
     */
    public static boolean isEmpty(Editable value){
        return value == null || value.toString().isEmpty();
    }

    /**
     * Replace a String with a default value when string param is null
     * @param value The string
     * @param defaultValue The string to use when value is null
     * @return The replaced string
     */
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
