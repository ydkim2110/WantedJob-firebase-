package com.example.anti2110.wantedjob.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anti2110.wantedjob.todo.Task;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper{



    private static final String DB_NAME = "wanted_job.db";
    private static final int DB_VER = 1;
    public static final String DB_TABLE = "TASK";
    public static final String COL_1 = "TASK_NAME";
    public static final String COL_2 = "TASK_MEMO";
    public static final String COL_3 = "TASK_END_DATE";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT, %s TEXT);", DB_TABLE, COL_1, COL_2, COL_3);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public boolean insertNewTask(String task_name, String task_memo, String task_end_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, task_name);
        values.put(COL_2, task_memo);
        values.put(COL_3, task_end_date);
        long result = db.insert(DB_TABLE, null, values);
        if(result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public void deleteTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, COL_1 + " = ?", new String[]{task});
        db.close();
    }

    public List<Task> getTaskList() {
        List<Task> taskList = new ArrayList<>();

        String query = String.format("SELECT * FROM %s", DB_TABLE);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Task task = null;

        while (cursor.moveToNext()) {
            task = new Task();
            task.setTask_name(cursor.getString(1));
            task.setTask_memo(cursor.getString(2));
            task.setTask_end_date(cursor.getString(3));

            taskList.add(task);
        }

        cursor.close();
        db.close();
        return taskList;
    }

}
