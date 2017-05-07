package com.example.teamabstraction.livestockmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lindseyballard on 5/4/17.
 */


public class ProfitUtil {
    public static String calculateProfit (Context context, String animal) {
        DatabaseHelper mydb;
        mydb = new DatabaseHelper(context);

        final Cursor pdate = mydb.getAnimalPurchaseDate();
        final Cursor pprice = mydb.getAnimalPurchasePrice();
        final Cursor spprice = mydb.getAnimalSellingPrice(); // newly added for selling price
        final Cursor freg = mydb.getAnimalFeedRegiment();
        final Cursor famount = mydb.getAnimalFeedAmount();
        final Cursor fcost = mydb.getAnimalFeedCost();

        String pd = pdate.getString(0);
        String pp = pprice.getString(0);
        String sp = spprice.getString(0);
        String fr = freg.getString(0);
        String fa = famount.getString(0);
        String fc = fcost.getString(0);

        Double profit = 0.00;
        String profitString;

        if (pp == null || pp.equalsIgnoreCase("")) {
            pp = "0.00";
        }

        if (sp == null || sp.equalsIgnoreCase("")) {
            sp = "0.00";
        }

        if (fr == null || fr.equalsIgnoreCase("")) {
            fr = "0.00";
        }

        if (fa == null || fa.equalsIgnoreCase("")) {
            fa = "0.00";
        }

        if (fc == null || fc.equalsIgnoreCase("")) {
            fc = "0.00";
        }



        Double feedCostPerBag = Double.parseDouble(fc);
        Double feedLbsPerBag = Double.parseDouble(fa);
        Double feedPoundsPerDay = Double.parseDouble(fr);
        Double purchasePrice = Double.parseDouble(pp);
        Double sellingPrice = Double.parseDouble(sp);
        Date purchaseDateDate = DateUtil.stringToDate(pd);
        Calendar purchaseDateCal = DateUtil.dateToCalendar(purchaseDateDate);
        Calendar currentDate = Calendar.getInstance(Locale.getDefault());
        int daysOwned = DateComparator.daysOwned(currentDate, purchaseDateCal);
        double daysOwnedDouble = (double) daysOwned;



        Double costPerPound = feedCostPerBag/feedLbsPerBag;
//        System.out.println("Cost Per Pound: " + costPerPound);

        Double feedCostPerDay = feedPoundsPerDay*costPerPound;
//        System.out.println("feedCostPerDay: " + feedCostPerDay);

        System.out.println("Days Owned: " + daysOwnedDouble);
        Double totalRunningFeedCost = daysOwnedDouble*feedCostPerDay;
//        System.out.println("Total Running Feed Cost: " + totalRunningFeedCost);

//        System.out.println("Profit Before Calc: " + profit);
        profit = -1*(totalRunningFeedCost+purchasePrice)+sellingPrice;
        System.out.println("Profit After Calc: " + profit);

        profitString = profit.toString();

        return profitString;
    }
}
