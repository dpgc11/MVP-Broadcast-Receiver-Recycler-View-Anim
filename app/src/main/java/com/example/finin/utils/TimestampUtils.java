package com.example.finin.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by yogesh on 5/7/17.
 * Methods for dealing with timestamps
 */
public class TimestampUtils {

    /**
     * Private constructor: class cannot be instantiated
     */
    private TimestampUtils() {
    }

    /**
     * Return an ISO 8601 combined date and time string for current date/time
     *
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    public static String getISO8601StringForCurrentDate() {
        Date now = new Date();
        return getISO8601StringForDate(now);
    }

    /**
     * Return an ISO 8601 combined date and time string for specified date/time
     *
     * @param date Date
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    public static String getISO8601StringForDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String parseUnixTimeStamp(Long timestamp, String pattern) {

        String result;
        Date date = new Date(timestamp);

// Create an instance of SimpleDateFormat used for formatting
// the string representation of date according to the chosen pattern
        SimpleDateFormat df = new SimpleDateFormat(pattern);

// Using DateFormat format method we can create a string
// representation of a date with the defined format.
        result = df.format(date);
//        Timber.d("parseUnixTimeStamp: result: %s", result);

        return result;
    }
}
