package com.example.teamabstraction.livestockmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lindseyballard on 4/24/17.
 */

// this class will be used to convert the string entered into purchase date
// into a date object for comparison against current date to determine
// daysOwned for profit calculations.
public class StringToDate {
    public static void main(String[] args) throws Exception {
        String sDate1 = "12/12/2010"; // pull this form the db for each animal
        Date date1 = new SimpleDateFormat("mm/dd/yyyy").parse(sDate1);
    }
}
