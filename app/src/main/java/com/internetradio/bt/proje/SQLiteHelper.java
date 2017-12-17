package com.internetradio.bt.proje;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by HakanKurt on 17.12.2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String radyoAd,String radyoUrl,byte[] radyoImg,String radyoKategori){
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO RADYO VALUES (NULL, ?, ?, ?, ?)";

        SQLiteStatement statement= database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,radyoAd);
        statement.bindString(2,radyoUrl);
        statement.bindBlob(3,radyoImg);
        statement.bindString(4,radyoKategori);

        statement.executeInsert();
    }

   /* public void deleteData(int id){
        SQLiteDatabase database=getWritableDatabase();

        String sql="DELETE FROM RADYO WHERE id = ?"
    }*/

    public Cursor getData(String sql)
    {
        SQLiteDatabase database=getReadableDatabase();

        return database.rawQuery(sql, null);
    }
    @Override

    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
