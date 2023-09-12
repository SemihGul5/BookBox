package com.example.bookbox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DBNAME= "book.db";
    public static final String TABLENAME="book";
    public static final int VER=1;
    public DbHelper(@Nullable Context context) {
        super(context, TABLENAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table "+TABLENAME+" (id integer primary key, image blob,name text, writer text, category text, date text, publisher text, description text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql="drop table if exists "+TABLENAME+" ";
        sqLiteDatabase.execSQL(sql);
    }
}
