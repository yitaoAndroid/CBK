package com.example.administrator.cbkproject.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/6/22.
 */
public class MyProvider extends ContentProvider {
    private MyHelper myHelper;
    @Override
    public boolean onCreate() {
        myHelper = new MyHelper(getContext());
        return true;
    }

   private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        matcher.addURI("com.cbk.pro","collection",1);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = myHelper.getReadableDatabase();
        Cursor cursor = database.query("collection", projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = myHelper.getReadableDatabase();
        int code = matcher.match(uri);
        long id = 0;
        if(code == 1){
            id =  database.insert("collection",null,values);
        }
       Uri url =  ContentUris.withAppendedId(uri, id);
        return url;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = myHelper.getReadableDatabase();
        int code = matcher.match(uri);
        int count = 0;
        if(code == 1){
         count =  database.delete("collection",selection,selectionArgs);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = myHelper.getReadableDatabase();
        int code = matcher.match(uri);
        int count = 0;
        if(code == 1){
            count =  database.update("collection", values, selection, selectionArgs);
        }
        return count;
    }
}
