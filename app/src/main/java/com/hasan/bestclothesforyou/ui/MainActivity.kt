package com.hasan.bestclothesforyou.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.hasan.bestclothesforyou.data.WeatherData
import com.hasan.bestclothesforyou.databinding.ActivityMainBinding
import com.hasan.bestclothesforyou.util.Constraint
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        println("test git ")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeRequestOfWeatherApiOKHTTP()
    }

    private fun makeRequestOfWeatherApiOKHTTP(){
        val requestWeatherApi = Request.Builder()
            .url("http://api.weatherstack.com/current?access_key=${Constraint.API_WEATHER_KEY}&query=${Constraint.LOCATION_CITY_WEATHER}")
            .build()
        client.newCall(requestWeatherApi).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.i("Hasan", "Fail request")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseApi = response.body?.string()
                val gson = Gson()
                val weatherData = gson.fromJson(responseApi, WeatherData::class.java)
                    runOnUiThread {
                        binding.textView.text = weatherData.current.observationTime
                    }
                }


        })
    }

}