package com.example.teamabstraction.livestockmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
    public static final String Col_6 = "Purchase Date";

    public static final String Table_Type = "Animal";
    public static final String TType = "Animal Type";
    public static final String TName = "Name";

    public static final String Table_Feed = "Feed";
    public static final String FRegiment = "Feed Regiment";
    public static final String FName = "Name";
    public static final String FAmount = "Amount in lbs";
    public static final String FCost = "Cost";

    public static final String Table_Profits = "Profits";
    public static final String PFeed_Regiment = "Feed Regiment";
    public static final String PAnimal_Name = "Name";
    public static final String PGain = "Gain";
    public static final String PCost = "Cost";

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
        db.execSQL("create table " + Table_NAME + "(Name TEXT, Breed TEXT, Gender TEXT, NChildren TEXT, Product TEXT)");
        db.execSQL("create table " + Table_Feed + "(FRegiment TEXT, FName TEXT, FAmount TEXT, FCost)");
        // Jimmie- look over this statement and fix with the correct variables that are in the DB

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists " + Table_NAME);
        db.execSQL("Drop Table If Exists " + Table_Feed);
        onCreate(db);
    }

    //TODO: rename to insertAnimal
    public boolean insertAnimalData(String Name, String Breed, String Gender, String NChildren, String Product, String PurchaseDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1, Name);
        contentValues.put(Col_2, Breed);
        contentValues.put(Col_3, Gender);
        contentValues.put(Col_4, NChildren);
        contentValues.put(Col_5, Product);
        contentValues.put(Col_6, PurchaseDate);

        long result = db.insert(Table_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertFeedData(String FeedName, String FeedAmount, String FeedRegiment, String FeedCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FName, FeedName);
        contentValues.put(FAmount, FeedAmount);
        contentValues.put(FRegiment, FeedRegiment);
        contentValues.put(FCost, FeedCost);

        long result = db.insert(Table_Feed, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //TODO: insert feed method similar to method above

    public Cursor getAnimalData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(Table_NAME, new String[] {"Name"}, null, null, null, null, null);
        if (res != null)
            res.moveToFirst();
        return res;
    }
}