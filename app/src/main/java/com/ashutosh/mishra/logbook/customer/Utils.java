package com.ashutosh.mishra.logbook.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Ashutosh on 17-08-2016.
 */
public class Utils {

    public static String getLastDay(String dateString) {


        String year = "", month = "";

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString);
            year += date.getYear();
            month += date.getMonth();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // get a calendar object
        GregorianCalendar calendar = new GregorianCalendar();

        // convert the year and month to integers
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);

        // adjust the month for a zero based index
        monthInt = monthInt - 1;

        // set the date of the calendar to the date provided
        calendar.set(yearInt, monthInt, 1);

        int dayInt = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return Integer.toString(dayInt);
    }
}
