package com.example.teamabstraction.livestockmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lindseyballard on 4/24/17.
 */


public class DateUtil {

    // this method will be used to convert the string entered into purchase date
    // into a date object for comparison against current date to determine
    // daysOwned for profit calculations.
    public static Date stringToDate(String inputDate) {
        // pull this form the db for each animal
        try {
            Date date1 = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH).parse(inputDate);
            return date1;
        } catch (ParseException e) {
            System.out.println("ERROR - " + e.getMessage());
            return null;
        }
    }

    public static Calendar dateToCalendar(Date inputDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        return cal;
    }

}