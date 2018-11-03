package com.AndroidAssistant;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ContentProviderTest extends ContentProvider {
    private static final UriMatcher uriMatch = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int QUERYSUCCESSCODE = 1;
    private static final int INSERTSUCCESSCODE = 2;
    private static final int UPDATESUCCESSCODE = 3;
    private static final int DELETESUCCESSCODE = 4;

    static {
        uriMatch.addURI("com.AndroidAssistant.provider","qury",QUERYSUCCESSCODE);
        uriMatch.addURI("com.AndroidAssistant.provider","insert",INSERTSUCCESSCODE);
        uriMatch.addURI("com.AndroidAssistant.provider","update",UPDATESUCCESSCODE);
        uriMatch.addURI("com.AndroidAssistant.provider","delete",DELETESUCCESSCODE);
    }

    private ContentProviderSqlite sqliteHelper;

    @Override
    public String getType(Uri uri) {
        return null;
    }

    // 创建数据库对象
    @Override
    public boolean onCreate() {

        sqliteHelper = new ContentProviderSqlite(getContext());

        return false;
    }

    // 实现内容提供者的查询方法，供其他应用使用
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = sqliteHelper.getReadableDatabase();
        // 和其他应用传过来的uri匹配
        int code =  uriMatch.match(uri);

        if (code == QUERYSUCCESSCODE){
            // 匹配成功,开始查询
            Cursor myCursor = database.query("info",projection,selection,selectionArgs,null,null,sortOrder);

            // 数据库被查询，发送通知,告诉内容观察者
            getContext().getContentResolver().notifyChange(uri,null);
            return myCursor;
        }else {
            // 匹配不成功
            return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = uriMatch.match(uri);
        if (code == INSERTSUCCESSCODE){
            SQLiteDatabase database = sqliteHelper.getWritableDatabase();
            long result = database.insert("info",null,values);

            // 数据库被插入数据，发送通知,告诉内容观察者
            getContext().getContentResolver().notifyChange(uri,null);

            return Uri.parse(result + "");
        }else {
            return Uri.parse("-1");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = uriMatch.match(uri);
        if (code == DELETESUCCESSCODE){
            SQLiteDatabase database = sqliteHelper.getWritableDatabase();
            int rowNum = database.delete("info",selection,selectionArgs);

            // 数据库被删除记录，发送通知,告诉内容观察者
            getContext().getContentResolver().notifyChange(uri,null);

            return  rowNum;
        }else {
            return -1;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int code = uriMatch.match(uri);
        if (code == UPDATESUCCESSCODE){
            SQLiteDatabase database = sqliteHelper.getWritableDatabase();
            int result = database.update("info",values,selection,selectionArgs);

            // 数据库被更新，发送通知,告诉内容观察者
            getContext().getContentResolver().notifyChange(uri,null);

            return result;
        }else {
            return -1;
        }
    }
}
