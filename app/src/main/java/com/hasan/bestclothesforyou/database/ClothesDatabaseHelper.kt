package com.hasan.bestclothesforyou.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClothesDatabaseHelper(context : Context) : SQLiteOpenHelper(context, DBNAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = buildString {
        append("CREATE TABLE IF NOT EXISTS $TABLE_NAME (")
        append("$ID INTEGER PRIMARY KEY AUTOINCREMENT,")
        append("$NAME TEXT,")
        append("$IMAGE BLOB,")
        append("$CATEGORY TEXT,")
        append("$WEATHER_DEGREE TEXT,")
        append("$SEASON_TYPE TEXT")
        append(")")
    }
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object{
        private const val DBNAME = "ClothesDataBase"
        private const val DB_VERSION = 1


        private const val TABLE_NAME = "Clothes"
        private const val ID = "id"
        private const val NAME = "name"
        private const val IMAGE = "image"
        private const val CATEGORY = "category"
        private const val WEATHER_DEGREE = "weather_degree"
        private const val SEASON_TYPE = "seasonType"
    }
}

