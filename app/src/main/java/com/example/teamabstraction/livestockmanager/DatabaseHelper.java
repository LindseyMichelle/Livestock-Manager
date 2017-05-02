package com.example.teamabstraction.livestockmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DisplayContext;
import android.util.Log;

// TODO: Make sure all variables that are input are being stored in DB (line 64)
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LivestockManager";

    // add date of purchase
    public static final String Table_NAME = "Animal";
    public static final String Col_1 = "Name";
    public static final String Col_2 = "Breed";
    public static final String Col_3 = "Gender";
    public static final String Col_4 = "NChildren";
    public static final String Col_5 = "Product";
    public static final String Col_6 = "PurchaseDate";
//    public static final String Col_7 = "FName";
//    public static final String Col_8 = "Feed Regiment";
//    public static final String Col_9 = "FAmount";
//    public static final String Col_10 = "FCost";
    public static final String Col_11 = "PurchasePrice";
    public static final String Col_14 = "SellingPrice"; // use in specAnimalView
    public static final String Col_13 = "AType";

    public static final String Table_Type = "AnimalType";
    public static final String TType = "AnimalType";
    public static final String TNumber = "NumberOf";


    public static final String Table_Feed = "Feed";
    public static final String Col_20 = "FRegiment"; //Feed Amount/Day
    public static final String Col_21 = "FName";
    public static final String Col_22 = "FAmount"; // Pounds/Bag of Feed
    public static final String Col_23 = "FCost"; // Cost/bag of feed
    public static final String Col_24 = "AName";

    public static final String Table_Profits = "Profits";
    public static final String PFeed_Regiment = "Feed Regiment";
    public static final String PAnimal_Name = "AName";
    public static final String PProfit = "ProfitToDate";
    public static final String PCost = "Cost"; //cost to date
    public static final String PDaysOwned = "DaysOwned"; //calculated # of days owned

    public static final String Table_Field = "Field";
    public static final String FLocation = "Location";
    public static final String FType = "Type";
    public static final String FAnimal_Name = "Name";

    public static final String Table_Product = "Product";
    public static final String PName = "Name";
    public static final String PProduct_Name = "Product";
    public static final String PPrice = "Price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_NAME + "(Name TEXT, Breed TEXT, Gender TEXT, NChildren TEXT," +
                " Product TEXT, PurchasePrice TEXT, PurchaseDate TEXT, SellingPrice TEXT, AType TEXT)");
        db.execSQL("create table " + Table_Type + "(AnimalType TEXT, NumberOf TEXT)");
        db.execSQL("create table " + Table_Feed + "(FRegiment TEXT, FName TEXT, FAmount TEXT, FCost TEXT, AName TEXT)");
        db.execSQL("create table " + Table_Profits + "(AName TEXT, ProfitToDate TEXT, Cost TEXT, DaysOwned TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists " + Table_NAME);
        db.execSQL("Drop Table If Exists " + Table_Feed);
        db.execSQL("Drop Table If Exists " + Table_Type);
        db.execSQL("Drop Table If Exists " + Table_Profits);
        onCreate(db);
    }



    public boolean insertAnimalData(String Name, String Breed, String Gender, String NChildren,
                                    String Product, String PurchaseDate, String PurchasePrice,
                                    String AType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1, Name);
        contentValues.put(Col_2, Breed);
        contentValues.put(Col_3, Gender);
        contentValues.put(Col_4, NChildren);
        contentValues.put(Col_5, Product);
        contentValues.put(Col_6, PurchaseDate);
        contentValues.put(Col_11, PurchasePrice);
        contentValues.put(Col_13, AType);

        try {
            long result = db.insertOrThrow(Table_NAME, null, contentValues);
        } catch(SQLException exception) {
            Log.v("SQL Exception", exception.getLocalizedMessage());
            return false;
        }

        return true;
    }

// TODO: this isn't working.
    public boolean insertFeedData(String FName, String FAmount, String FRegiment, String FCost, String AName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_20, FName);
        contentValues.put(Col_21, FRegiment); // amount/day
        contentValues.put(Col_22, FAmount); // lbs/bag
        contentValues.put(Col_23, FCost); // cost/bag
        contentValues.put(Col_24, AName);

        try {
            long result = db.insertOrThrow(Table_Feed, null, contentValues);
        } catch(SQLException exception) {
            Log.v("SQL Exception", exception.getLocalizedMessage());
            return false;
        }

        return true;
    }

    public boolean insertSellingPrice(String SellingPrice, String AType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_14, SellingPrice);
        contentValues.put(Col_13, AType); //

        try {
            long result = db.insertOrThrow(Table_NAME, null, contentValues);
        } catch(SQLException exception) {
            Log.v("SQL Exception", exception.getLocalizedMessage());
            return false;
        }

        return true;
    }


    public void deleteAnimal(String Name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_NAME, Col_1 + "=?", new String[] {Name});
        return;
    }

    public boolean insertAnimalType(String AnimalType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TType, AnimalType);

        try {
            long result = db.insertOrThrow(Table_Type, null, contentValues);
        } catch(SQLException exception) {
            Log.v("SQL Exception", exception.getLocalizedMessage());
            return false;
        }
        return true;
    }


    //TODO: insert feed method similar to method above

    public void deleteAnimalType(String type){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Type, TType + "=?", new String[] {type});
        db.delete(Table_NAME, Col_13 + "=?", new String[] {type});
        return;
    }


    public Cursor getAnimalData() {
        SQLiteDatabase db = this.getWritableDatabase();

        String type = GlobalVariables.getInstance().aType;
        Cursor res = db.query(Table_NAME, new String[] {"Name"}, Col_13 +"=?", new String[] {type}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }


    public Cursor getAnimalTypes() {
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor res2 = db2.query(Table_Type, new String[]{"AnimalType"}, null, null, null, null, null);
        if (res2 != null)
            res2.moveToFirst();
        return res2;
    }

    public Cursor getAnimalPurchaseInfo() {
        SQLiteDatabase db3 = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res3 = db3.query(Table_NAME, new String[]{"PurchasePrice"},
                Col_24 +"=?", new String[] {name}, null, null, null);
        if (res3 != null)
            res3.moveToFirst();
        return res3;
    }

    public Cursor getAnimalFeedInfo() {
        SQLiteDatabase db4 = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res4 = db4.query(Table_Feed, new String[]{"FCost, FAmount, FRegiment"},
                Col_24 +"=?", new String[] {name}, null, null, null);
        if (res4 != null)
            res4.moveToFirst();
        return res4;
    }
}