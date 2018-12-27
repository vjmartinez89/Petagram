package com.vjmartinez.petagram.utils;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final String DEFAULT_FORMAT = "dd/MM/yyyy";

    /**
     * Format a date object to pattern string
     * @param date The date to format
     * @param pattern The format String
     * @return The formatted String
     */
    public static String formatDate(Date date, @Nullable String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * Parse date from formatted String
     * @param date The string to parse
     * @param inputFormat The input format of String
     * @return The Date object
     * @throws ParseException When inputFormat or String date are not valid
     */
    public static Date parseDate(String date, String inputFormat) throws ParseException{
        return  new SimpleDateFormat(inputFormat).parse(date);
    }
}
