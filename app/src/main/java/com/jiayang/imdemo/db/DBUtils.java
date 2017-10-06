package com.jiayang.imdemo.db;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： itheima
 * 时间：2016-10-17 15:53
 * 网址：http://www.itheima.com
 */

public class DBUtils {

    private static Context sContext;
    private static boolean isInit;

    public static void initDB(Context context) {
        sContext = context.getApplicationContext();
        isInit = true;
    }

    public static List<String> getContacts(String username) {
        if (!isInit) {
            throw new RuntimeException("使用DBUtils之前请 现在Application中初始化！");
        }
        ContactSQLiteOpenHelper openHelper = new ContactSQLiteOpenHelper(sContext);
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(ContactSQLiteOpenHelper.T_CONTACT, new String[]{ContactSQLiteOpenHelper.CONTACT}, ContactSQLiteOpenHelper.USERNAME + "=?", new String[]{username}, null, null, ContactSQLiteOpenHelper.CONTACT);
        List<String> contactsList = new ArrayList<>();
        while(cursor.moveToNext()){
            String contact = cursor.getString(0);
            contactsList.add(contact);
        }
        cursor.close();
        database.close();
        return contactsList;
    }

    /**
     *
     * @param username
     * @param contactsList
     *
     *  1. 先删除username的所有的联系人
     * 2. 再添加contactsList添加进去
     *
     */
    public static void updateContacts(String username,List<String> contactsList){
        ContactSQLiteOpenHelper openHelper = new ContactSQLiteOpenHelper(sContext);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.beginTransaction();
        database.delete(ContactSQLiteOpenHelper.T_CONTACT,ContactSQLiteOpenHelper.USERNAME+"=?",new String[]{username});
        ContentValues values = new ContentValues();
        values.put(ContactSQLiteOpenHelper.USERNAME,username);
        for (int i = 0; i < contactsList.size(); i++) {
            String contact = contactsList.get(i);
            values.put(ContactSQLiteOpenHelper.CONTACT,contact);
            database.insert(ContactSQLiteOpenHelper.T_CONTACT,null,values);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }

}
