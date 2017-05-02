package com.example.teamabstraction.livestockmanager;

import android.app.Application;

/**
 * Created by OldNo.7 on 4/23/2017.
 */

public class GlobalVariables {
    private static GlobalVariables mInstance= null;

    public String aType;
    public String aName;
    public boolean edit;
    public boolean change;

    protected GlobalVariables(){}

    public static synchronized GlobalVariables getInstance(){
        if(null == mInstance){
            mInstance = new GlobalVariables();
        }
        return mInstance;
    }
}
