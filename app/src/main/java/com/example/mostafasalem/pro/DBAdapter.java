package com.example.mostafasalem.pro;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
    public static final String KEY_NAME = "name";
    public static final String KEY_PASS = "password";
    public static final String KEY_Phone = "phone";
    public static final String KEY_Visa = "visa";
    public static final String KEY_Paid_Books = "paid_books";
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "DB_BD";
    private static final String DATABASE_TABLE = "Users";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "create table Users ( "
                    + "name text not null , password text not null , phone text not null ,visa text , paid_books text);";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS studens");
            onCreate(db);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //---insert a contact into the database---
    public long insertStudent( String name, String password , String phone)
    {

        ContentValues initialValues = new ContentValues();
        //initialValues.put(KEY_ROWID, id);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PASS, password);
        initialValues.put(KEY_Phone, phone);
        return db.insert(DATABASE_TABLE, null, initialValues);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //---deletes a particular contact---
    /*public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }*/

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //---retrieves all the contacts---

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //---retrieves a particular contact---
    public Cursor getContact(String rowName) throws SQLException
    {

        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                                KEY_NAME,KEY_PASS}, KEY_NAME + "=" + rowName, null,
                        null, null, null, null);
                /*db.rawQuery("select * from "+ DATABASE_TABLE +" where "+ rowName);*/

        /*if (mCursor != null) {
            mCursor.moveToFirst();
        }*/
        return mCursor;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //---updates a contact---
    public boolean updateContact( String name , String visa , String paid_books){
        ContentValues value = new ContentValues();
        value.put(KEY_Visa, visa);
        value.put(KEY_Paid_Books, paid_books);


        db.update(DATABASE_TABLE, value, " name = ? ", new String[]{name});

        return true;

        //return db.update(DATABASE_TABLE, value, KEY_NAME + "=" + name, null) > 0;
    }




    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public Cursor getUser(String rowName,String rowPassword) throws SQLException
    {

        Cursor mCursor =
              db.rawQuery("select * from "+ DATABASE_TABLE +" where "+ KEY_NAME +" = ? AND " +KEY_PASS + " = ? "
                      ,new String[]{rowName ,rowPassword} ,null );

        return mCursor;
    }

}