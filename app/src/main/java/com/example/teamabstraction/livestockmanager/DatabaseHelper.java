package com.example.teamabstraction.livestockmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    public static final String Col_23 = "FCost";// Cost/bag of feed
    public static final String Col_24 = "Animal"; // match feed info with an animal

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
        db.execSQL("create table " + Table_Feed + "(FRegiment TEXT, FName TEXT, FAmount TEXT, FCost TEXT, Animal TEXT)");
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
        contentValues.put(Col_13, AType); // TODO: should be its own function inserttype

        GlobalVariables.getInstance().aName = Name;

        try {
            long result = db.insertOrThrow(Table_NAME, null, contentValues);
        } catch(SQLException exception) {
            Log.v("SQL Exception", exception.getLocalizedMessage());
            return false;
        }

        return true;
    }


    public boolean insertFeedData(String FName, String FAmount, String FRegiment, String FCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_21, FName);
        contentValues.put(Col_20, FRegiment); // amount/day
        contentValues.put(Col_22, FAmount); // lbs/bag
        contentValues.put(Col_23, FCost); // cost/bag
        contentValues.put(Col_24, GlobalVariables.getInstance().aName);

        try {
            long result = db.insertOrThrow(Table_Feed, null, contentValues);
        } catch(SQLException exception) {
            Log.v("SQL Exception", exception.getLocalizedMessage());
            return false;
        }

        return true;
    }


    public boolean insertProfit (String ProfitToDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PProfit, ProfitToDate);
        contentValues.put(PAnimal_Name, GlobalVariables.getInstance().aName);

        try {
            long result = db.insertOrThrow(Table_Profits, null, contentValues);
        } catch(SQLException exception) {
            Log.v("SQL Exception", exception.getLocalizedMessage());
            return false;
        }

        return true;
    }

    // make this an update instead of insert
    public boolean updateSellingPrice(String SellingPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_14, SellingPrice);

        try {
            long result = db.update(Table_NAME, contentValues, Col_1 + " =?", new String[]{GlobalVariables.getInstance().aName});
        } catch(SQLException exception) {
            Log.v("SQL Exception", exception.getLocalizedMessage());
            return false;
        }

        return true;
    }


    public boolean updateAnimalTable(String Name, String Breed, String Gender, String NChildren,
                                String Product, String PurchaseDate, String PurchasePrice,
                                String AType){
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

        GlobalVariables.getInstance().aName = Name;

        long result = db.update(Table_NAME, contentValues, Col_1 + " =?", new String[]{Name});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateFeedData(String FName, String FAmount, String FRegiment, String FCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_21, FName);
        contentValues.put(Col_20, FRegiment); // amount/day
        contentValues.put(Col_22, FAmount); // lbs/bag
        contentValues.put(Col_23, FCost); // cost/bag
        contentValues.put(Col_24, GlobalVariables.getInstance().aName);

        long result = db.update(Table_Feed, contentValues, Col_24 + " =?", new String[]{GlobalVariables.getInstance().aName});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
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

        long result = db.insert(Table_Type, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }



    public void deleteAnimalType(String type){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Type, TType + "=?", new String[] {type});
        db.delete(Table_NAME, Col_13 + "=?", new String[] {type});
        return;
    }


    public Cursor getAnimalNames() {
        SQLiteDatabase db = this.getWritableDatabase();

        String type = GlobalVariables.getInstance().aType;
        Cursor res = db.query(Table_NAME, new String[] {"Name"}, Col_13 +"=?", new String[] {type}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalBreed() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_NAME, new String[] {"Breed"}, Col_1 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalGender() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_NAME, new String[] {"Gender"}, Col_1 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }
    public Cursor getAnimalNChildren() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_NAME, new String[] {"NChildren"}, Col_1 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalProduct() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_NAME, new String[] {"Product"}, Col_1 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalPurchaseDate() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_NAME, new String[] {"PurchaseDate"}, Col_1 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalPurchasePrice() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_NAME, new String[] {"PurchasePrice"}, Col_1 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalSellingPrice() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_NAME, new String[] {"SellingPrice"}, Col_1 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalFeedName() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_Feed, new String[] {"FName"}, Col_24 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalFeedRegiment() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_Feed, new String[] {"FRegiment"}, Col_24 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalFeedAmount() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_Feed, new String[] {"FAmount"}, Col_24 +"=?", new String[] {name}, null, null,  null);

        if (res != null)
            res.moveToFirst();
        return res;
    }

    public Cursor getAnimalFeedCost() {
        SQLiteDatabase db = this.getWritableDatabase();

        String name = GlobalVariables.getInstance().aName;
        Cursor res = db.query(Table_Feed, new String[] {"FCost"}, Col_24 +"=?", new String[] {name}, null, null,  null);

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

    public Cursor getAllProfits() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(Table_Profits, new String[]{"ProfitToDate"}, null, null, null, null, null);

        if (res != null)
            res.moveToFirst();
        return res;
    }
}