package com.example.fitinsevenminutes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1   //This Database Version
        private val DATABASE_NAME = "FitInSevenMinutes.db"   //Name of the database
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id" //Column Id
        private val COLUMN_COMPLETED_DATE = "completed_date" //column for completed date
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXCERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE + " TEXT)")
        db?.execSQL(CREATE_EXCERCISE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date: String) {
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE, date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }

    fun getAllCompleteDateList(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        while(cursor.moveToNext()) {
            val dateValue = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            list.add(dateValue)
        }
        cursor.close()
        return list
    }

}