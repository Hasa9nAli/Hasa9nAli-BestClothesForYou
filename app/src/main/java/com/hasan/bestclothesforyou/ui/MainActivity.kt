package com.hasan.bestclothesforyou.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hasan.bestclothesforyou.data.WeatherData
import com.hasan.bestclothesforyou.databinding.ActivityMainBinding
import com.hasan.bestclothesforyou.util.Constraint
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private val client = OkHttpClient()
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                // update your view here
                // for example, if you have a TextView with id "myTextView":
                binding.textCurrentTime.text = getCurrentTime().removeRange(5, getCurrentTime().length)

                // schedule the task to run again in 1 minute
                handler.postDelayed(this, 60000)
                makeRequestOfWeatherApiOKHTTP()
            }
        }, 1)

    }
    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
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
                        binding.textCurrentTime.text = LocalTime.now().toString().removeRange(5,LocalTime.now().toString().length )
//                        Glide.with(binding.imageView.context).load(weatherData.current.weatherIcons[0]).into(binding.imageView)
                    }
                }



        })
    }

}