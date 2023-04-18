package com.hasan.bestclothesforyou.util

import android.content.Context
import android.content.SharedPreferences

object PrefUtil{
    private var sharedPreferences : SharedPreferences? = null
    private const val DATE_INFORMATION = "Date Information"
    private const val CLOTHES_ID = "idClothesToday"

    fun initialPref (context : Context){
        sharedPreferences = context.getSharedPreferences(DATE_INFORMATION, Context.MODE_PRIVATE)
    }
    var idOfClothes : String?
        get() = sharedPreferences?.getString(CLOTHES_ID, null)
        set(value) { sharedPreferences?.edit()?.putString(CLOTHES_ID, value)?.apply()}


}