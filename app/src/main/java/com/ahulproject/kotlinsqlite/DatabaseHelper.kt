package com.ahulproject.kotlinsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Let's start by creating our database CRUD helper class
 * based on the SQLiteHelper
 */

class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, 1){

    /**
     * Our onCreate() menthod.
     * Called when the database is created for the first time. This is
     * Where the Creation of tables and initial populatiob of the tables
     * should happen
     */

    override fun onCreate(db: SQLiteDatabase){
        db.execSQL("CREATE TABLE $TABLE_NAME " +
                "($COL_1 INTEGER PRIMARY KEY, " +
                "$COL_2 TEXT," +
                "$COL_3 TEXT,"+
                "$COL_4 TEXT)")
    }

    /**
     * Let's create Our onUpgrade method
     * Called when the database needs to be upgraded. The implementation should
     * use this method to drop tables, add tables, or do anything else it needs
     * to upgrade to the new schema version.
     */

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME)
        onCreate(db)
    }

    /**
     * Let's created our insertData() method.
     * It will insert data to SQLite database.
     */

    fun insertData(name: String, surname: String, marks: String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        contentValues.put(COL_4, marks)
        db.insert(TABLE_NAME, null, contentValues)
    }

    /**
     * Let's create a method to update a row with new field values.
     */

    fun updateData(id: String, name: String, surname: String, marks: String) :
            Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        contentValues.put(COL_4, marks)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id: String) : Int{
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    /**
     * The below getter propety will return a Cursor containing our dataset.
     */
    val allData : Cursor
        get(){
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
            return res
        }

    /**
     * Let's create a companion object to hold our static fields.
     * A companion object is an object that is common to all instances of a given
     * class.
     */
    companion object{
        val DATABASE_NAME = "datapenting.db"
        val TABLE_NAME = "data_table"
        val COL_1 = "ID"
        val COL_2 = "NAME"
        val COL_3 = "NPM"
        val COL_4 = "JURUSAN"
    }
}
// End