package com.hasan.bestclothesforyou.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hasan.bestclothesforyou.util.DBTerms

class ClothesDbHelper(context : Context) : SQLiteOpenHelper(context, DBNAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS ${DBTerms.TABLE_NAME} (" +
                "${DBTerms.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DBTerms.NAME} TEXT," +
                "${DBTerms.IMAGE} BLOB," +
                "${DBTerms.CATEGORY} TEXT," +
                "${DBTerms.WEATHER_DEGREE} INTEGER," +
                "${DBTerms.SEASON_TYPE} TEXT" +
                ")"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    companion object{
        private const val DBNAME = "ClothesDataBase"
        private const val DB_VERSION = 1
    }

}