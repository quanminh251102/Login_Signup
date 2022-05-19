package com.example.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "ManageGarageDB.db";
    public  static final int DATABASE_VERSION = 1;

    //table account

    DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    private static final String CREATE_TABLE_ACCOUNT = "create Table Account(Username TEXT primary key, Password TEXT, Phonenumber TEXT, Fullname TEXT, Email TEXT, RegisterID TEXT)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        onCreate(db);
    }

    Cursor Read_Account(){
        String query = "Select * from Account";
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = null;
        if(MyDB != null)
        {
            cursor = MyDB.rawQuery(query,null);
        }
        return cursor;
    }

    public Boolean insertData(String username, String password, String email, String phonenumber, String fullname, String id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Username", username);
        contentValues.put("Password", password);
        contentValues.put("Phonenumber",phonenumber);
        contentValues.put("Fullname",fullname);
        contentValues.put("Email",email);
        contentValues.put("RegisterID", id);

        long result = MyDB.insert("Account",null,contentValues);
        if(result == -1) return false;
        else{
            return true;
        }
    }
    public Boolean checkusername(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Account where Username = ?",new String[] {username});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean check_email(String email)
    {
        SQLiteDatabase MyDB = getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Account where Email = ?",new String[]{email});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public String get_ID(String email)
    {
        SQLiteDatabase MyDB = getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Account where Email = ?",new String[]{email});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            return cursor.getString(5);
        }
        else
        {
            return null;
        }
    }
    public String get_Email(String ID)
    {
        SQLiteDatabase MyDB = getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Account where RegisterID = ?",new String[]{ID});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            return cursor.getString(4);
        }
        else
        {
            return "";
        }
    }
    public Boolean checkusernamepassword(String username,String password)
    {
        SQLiteDatabase MyDB = getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Account where Username = ? and Password = ?",new String[]{username,password});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean checkID(String id, String email)
    {
        SQLiteDatabase MyDB = getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Account where RegisterID = ? and Email = ?",new String[]{id,email});
        if(cursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean updateData(String id, String newpass)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Password",newpass);

        long result = MyDB.update("Account",contentValues,"RegisterID = ?",new String[]{id});
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
