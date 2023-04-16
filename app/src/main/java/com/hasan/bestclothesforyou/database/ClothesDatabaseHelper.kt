package com.hasan.bestclothesforyou.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.hasan.bestclothesforyou.data.ClothesData
import java.io.ByteArrayOutputStream
import java.lang.Math.random

class ClothesDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DBNAME, null, DB_VERSION) {

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

    fun addClothes(clothes: ClothesData) {
        val db = writableDatabase
        val values = ContentValues()

        // Convert Bitmap to byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        clothes.imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
        val imageByteArray = byteArrayOutputStream.toByteArray()
        values.put(ID, (random() * 1000).toString())
        values.put(NAME, clothes.name)
        values.put(IMAGE, imageByteArray)
        values.put(CATEGORY, clothes.category)
        values.put(WEATHER_DEGREE, "${clothes.weatherDegree.first}-${clothes.weatherDegree.second}")
        values.put(SEASON_TYPE, clothes.seasonType)
        db.insert(TABLE_NAME, null, values)
//        db.close()
    }

    @SuppressLint("Range")
    fun getAllClothes(): List<ClothesData> {
        val clothesList = mutableListOf<ClothesData>()
        val db = readableDatabase
        val selectAllQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor? = db.rawQuery(selectAllQuery, null)

        cursor?.use {
            // Iterate through cursor and create Clothes objects
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(ID)).toString()
                val name = it.getString(it.getColumnIndex(NAME))
                val imageByteArray = it.getBlob(it.getColumnIndex(IMAGE))
                val category = it.getString(it.getColumnIndex(CATEGORY))
                val seasonType = it.getString(it.getColumnIndex(SEASON_TYPE))
                val weatherDegreeString =
                    cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_DEGREE))
                val weatherDegree = Pair(
                    weatherDegreeString.substringBefore("-").toInt(),
                    weatherDegreeString.substringAfter("-").toInt()
                )
                // Convert byte array to Bitmap
                val imageBitmap =
                    BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                val clothes =
                    ClothesData(id, name, imageBitmap, weatherDegree, category, seasonType)
                clothesList.add(clothes)
            }
        }
        cursor?.close()
        db.close()
        return clothesList
    }

    companion object {
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

