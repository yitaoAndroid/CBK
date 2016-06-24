package com.example.administrator.cbkproject.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by Administrator on 2016/6/22.
 */
public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory().getAbsolutePath()+"/cbk/collection.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "create table if not exists collection(_id integer," +
                "title text not null,imagepath text ,key text not null,url text not null,time text " +
                "not null,hasread text not null)";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
