package com.smartechafghanistan.com.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="student.db";
    public static final String TABLE_INCOME="income_table";
    public static final String TABLE_OUTCOME="outcome_table";
    public static final String COL_IN_ID="income_id";
    public static final String COL_IN_NAME="name";
    public static final String COL_IN_COUNT="count";
    public static final String COL_IN_TYPE="type";


    public static final String COL_OUT_ID="outcome_id";
    public static final String COL_OUT_NAME="out_name";
    public static final String COL_OUT_COUNT="count";
    public static final String COL_OUT_TYPE="type";
    public static final String COL_OUT_FORNAME="name";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE_INCOME+ "(income_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,count INTEGER,type TEXT)");
        db.execSQL("CREATE TABLE " +TABLE_OUTCOME+ "(outcome_id INTEGER PRIMARY KEY AUTOINCREMENT,out_name TEXT,count INTEGER,type TEXT,name TEXT)");


    }

    public Boolean resetAll(String vendorName){
        try{
            SQLiteDatabase sqldb=this.getWritableDatabase();
            sqldb.execSQL("DELETE  FROM "+TABLE_INCOME+" WHERE name='"+vendorName+"'");
            sqldb.execSQL("DELETE  FROM "+TABLE_OUTCOME+" WHERE name='"+vendorName+"'");
            sqldb.close();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }





    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_OUTCOME);

    }

    public boolean insertData(String name,int count,String type){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_IN_NAME,name);
        contentValues.put(COL_IN_COUNT,count);
        contentValues.put(COL_IN_TYPE,type);
        long result= sqldb.insert(TABLE_INCOME,null,contentValues);
        sqldb.close();
        if(result==-1){
            return false;
        }
        else{
            return  true;
        }
    }

    public boolean insertData_out(String name,int count,String type,String forname){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_OUT_NAME,name);
        contentValues.put(COL_OUT_COUNT,count);
        contentValues.put(COL_OUT_TYPE,type);
        contentValues.put(COL_OUT_FORNAME,forname);
        long result= sqldb.insert(TABLE_OUTCOME,null,contentValues);
        sqldb.close();
        if(result==-1){
            return false;
        }
        else{
            return  true;
        }
    }



    public Cursor getAllIncome(){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        Cursor rs=sqldb.rawQuery("SELECT * from "+TABLE_INCOME,null);
        return rs;
    }


    /// get sum for show activity


    public Cursor getSumOfMoneyForShowActivity(String table_name,String type,String name){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        Cursor rs=sqldb.rawQuery("SELECT SUM(count) from "+table_name+" where type='"+type+"'AND name='"+name+"'",null);
        return rs;
    }

    /// first sum

    public Cursor getSumOfIncomeTable(String table_name,String type){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        Cursor rs=sqldb.rawQuery("SELECT SUM(count) from "+table_name+" where type='"+type+"'",null);
        return rs;
    }



    public Cursor getSpecialOutcome(String vendorname){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        Cursor rs=sqldb.rawQuery("SELECT * from "+TABLE_OUTCOME+" WHERE name ='"+vendorname+"'",null);
        return rs;
    }

    public Cursor getSpecialOutcomeoffirstpage(String table_name,String type){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        Cursor rs=sqldb.rawQuery("SELECT name,SUM(count) from "+table_name+"  WHERE  type ='"+type+"' group by name ",null);
        return rs;
    }

    public Cursor getAllOutcome(){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        Cursor rs=sqldb.rawQuery("SELECT * from "+TABLE_OUTCOME,null);
        return rs;
    }
    public Cursor getName(){
        SQLiteDatabase sqldb=this.getWritableDatabase();
        Cursor rs=sqldb.rawQuery("SELECT name from "+TABLE_INCOME,null);
        return rs;

    }



}
