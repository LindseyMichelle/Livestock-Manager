package com.example.teamabstraction.livestockmanager;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by lindseyballard on 5/3/17.
 */

public class DateComparator {

    public static int daysOwned(Calendar currentDate, Calendar purchaseDate) {
        Calendar dayOne = (Calendar) currentDate.clone(),
                dayTwo = (Calendar) purchaseDate.clone();


        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }
            return ((extraDays - dayTwo.get(Calendar.DAY_OF_YEAR)) + dayOneOriginalYearDays);
        }
    }

//    public static long daysOwned(Date currentDate, Date purchaseDate) {
////
//        long diff = currentDate.getTime() - purchaseDate.getTime();
//        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);



//    public static int daysOwned(Calendar currentDate, Calendar purchaseDate) {
//        if (currentDate.get(Calendar.YEAR) != purchaseDate.get(Calendar.YEAR))
//            return currentDate.get(Calendar.YEAR) - purchaseDate.get(Calendar.YEAR);
//        if (currentDate.get(Calendar.MONTH) != purchaseDate.get(Calendar.MONTH))
//            return currentDate.get(Calendar.MONTH) - purchaseDate.get(Calendar.MONTH);
//        return currentDate.get(Calendar.DAY_OF_MONTH) - purchaseDate.get(Calendar.DAY_OF_MONTH);
//    }
}
