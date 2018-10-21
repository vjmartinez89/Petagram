package com.vjmartinez.petagram;

public class StringUtils {

    /**
     * True if a String is null or empty
     * @param value
     * @return
     */
    public static boolean isEmpty(String value){
        return value == null || value.isEmpty();
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
