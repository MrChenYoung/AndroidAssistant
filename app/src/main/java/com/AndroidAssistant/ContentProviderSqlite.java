package com.AndroidAssistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContentProviderSqlite extends SQLiteOpenHelper {
    public ContentProviderSqlite(Context context) {
        super(context, "MyDatabase.db", null, 1);
    }

    // 数据库创建的时候调用，适合初始化表结构
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT DEFAULT \"\"," + "age TEXT DEFAULT \"\")");
        // 初始化两条数据
        db.execSQL("insert into info(name,age) values (?,?)",new String[]{"张三","18"});
        db.execSQL("insert into info(name,age) values (?,?)",new String[]{"李四","20"});
    }

    // 数据库更新的时候调用，适合升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
