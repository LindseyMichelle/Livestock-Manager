package com.example.teamabstraction.livestockmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DisplayContext;

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
    public static final String Col_6 = "FName";
    public static final String Col_7 = "Feed Regiment";
    public static final String Col_8 = "FAmount";
    public static final String Col_9 = "FCost";
    public static final String Col_10 = "PPrice";
    public static final String Col_11 = "PDate";
    public static final String Col_12 = "AType";

    public static final String Table_Type = "AnimalType";
    public static final String TType = "AnimalType";
    public static final String TNumber = "NumberOf";

    public static final String Table_Feed = "Feed";
    public static final String FFeed_Regiment = "Feed Regiment";
    public static final String FFeed_Name = "Name";
    public static final String FAmount = "Amount in lbs";
    public static final String FCost = "Cost";

    // need to delete table
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
        db.execSQL("create table " + Table_NAME + "(Name TEXT, Breed TEXT, Gender TEXT, NChildren TEXT, Product TEXT, AType TEXT)");
        db.execSQL("create table " + Table_Type + "(AnimalType TEXT, NumberOf TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists " + Table_NAME);
        db.execSQL("Drop Table If Exists " + Table_Type);
        onCreate(db);
    }

    public boolean insertData(String Name, String Breed, String Gender, String NChildren, String Product, String AType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1, Name);
        contentValues.put(Col_2, Breed);
        contentValues.put(Col_3, Gender);
        contentValues.put(Col_4, NChildren);
        contentValues.put(Col_5, Product);
        contentValues.put(Col_12, AType);

        long result = db.insert(Table_NAME, null, contentValues);
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
        db.delete(Table_NAME, Col_12 + "=?", new String[] {type});
        return;
    }

    public Cursor getAnimalData() {
        SQLiteDatabase db = this.getWritableDatabase();

        String type = GlobalVariables.getInstance().aType;
        Cursor res = db.query(Table_NAME, new String[] {"Name"}, Col_12 +"=?", new String[] {type}, null, null,  null);

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

}