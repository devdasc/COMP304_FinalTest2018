package com.android.dev.devdaschatterjee_comp304_finaltest2018;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="TaskDB";
    public static  final String COLUMN_ID="taskId";
    public static  final String COLUMN_TASK_NAME="taskName";
    public static  final String COLUMN_TASK_DESC="taskDescription";
    public static final String TABLE_NAME="taskTable";
    public static  final String CREATE_TABLE="CREATE TABLE " + TABLE_NAME+" (taskId INTEGER PRIMARY KEY, taskName TEXT, taskDescription TEXT)";

    public TaskManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table if exists
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        // re-create the db
        onCreate(db);
    }
    //creates a task
    public boolean addTask(ContentValues values) throws Exception{
        SQLiteDatabase db= this.getWritableDatabase();
        long task=db.insert(TABLE_NAME,null,values);
        db.close();
        if(task==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor showAllTask(){
     SQLiteDatabase db = this.getReadableDatabase();
     String selectQuery="SELECT * FROM "+ TABLE_NAME;
     Cursor res = db.rawQuery(selectQuery,null);
     return res;
    }
    //fetch a task by ID
    public TaskModel getTaskById(int taskId){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery= "SELECT * FROM "+TABLE_NAME+ " WHERE " + COLUMN_ID+ " = "+ taskId;
        Cursor cursor=db.rawQuery(selectQuery,null);
        TaskModel taskModel=new TaskModel();
        if(cursor!=null){
            cursor.moveToFirst();
            taskModel.setTaskId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            taskModel.setTaskName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
            taskModel.setTaskDescription((cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESC))));
            cursor.close();
            //return taskModel;
        }else{
            taskModel=null;
        }
        db.close();
        return taskModel;
    }
    // edit a task
    public boolean editTask(Object obj, String fieldName, ContentValues values) throws Exception{
        SQLiteDatabase db=this.getWritableDatabase();
        int update= db.update(TABLE_NAME,values,fieldName+" = ? ",new String[]{String.valueOf(obj)});

        return update>0;
    }
    // delete a task
    public boolean deleteTask(int id) throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int deletedRow= db.delete(TABLE_NAME,"taskId = ?",new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return deletedRow>0;

    }

}





