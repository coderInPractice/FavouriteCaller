package com.example.favouritecaller.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favouriteContacts_db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "favourite_contacts";
   // public static final String CONTACTS_ID = "id";
    public static final String CONTACTS_NAME = "name";
    public static final String CONTACTS_NUMBER = "phone_number";
    public static final String CONTACTS_PHOTO_URI = "photo_uri";
    public static final String CONTACTS_PHOTO_ID = "photo_id";

    public ContactDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_query = "create table " + TABLE_NAME + "(" +
                CONTACTS_NAME + " text primary key  ," +
                CONTACTS_NUMBER + " text, " +
                CONTACTS_PHOTO_URI + " text, " +
                CONTACTS_PHOTO_ID  + " text " + ")";

        db.execSQL(create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }

    public void addContacts(ContactModel contactModel){
        SQLiteDatabase db = getWritableDatabase();
        String insert_contacts = "insert into " + TABLE_NAME +
                " values( '" + contactModel.getName()
                + "', '" + contactModel.getPhone_number() +
                "', '" + contactModel.getPhoto_uri()  +"', '" + contactModel.getPhotoId()  +
                "')";

        db.execSQL(insert_contacts);
        db.close();
    }

    public Cursor getAllContacts(){
        SQLiteDatabase db = getWritableDatabase();
        String get_all_query = "select * from " + TABLE_NAME;
        //db.close();
        return db.rawQuery(get_all_query,null);
    }
}
