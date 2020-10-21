package com.nkd.app.tasklistsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nkd.app.tasklistsqlite.model.Task;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "TASKS.DB";
    public static final int INT = 1;
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CONTENT = "CONTENT";
    public static final String COLUMN_STATUS = "STATUS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, INT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CONTENT + " TEXT," + COLUMN_STATUS + " BLOB)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertOne(String content, int status) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONTENT, content);
        cv.put(COLUMN_STATUS, status);
        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1) {
            return false;
        }
        db.close();
        return true;
    }

    public ArrayList<Task> findAllTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String content = cursor.getString(1);
                int status = cursor.getInt(2);
                Task row = new Task(id, content, status);
                tasks.add(row);
            } while (cursor.moveToNext());
        }
        return tasks;
    }

    public boolean deleteOne(int id) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
            db.close();
            return true;
        } catch (Exception e) {
        }
        db.close();
        return false;
    }

    public int deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            int numberRowEffected = db.delete(TABLE_NAME, "1", null);
            db.close();
            return numberRowEffected;
        } catch (Exception e) {
            db.close();
            throw new RuntimeException();
        }
    }

    public boolean updateStatus(int id, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, status);
        try {
            db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
