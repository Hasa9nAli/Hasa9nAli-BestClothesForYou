package com.hasan.bestclothesforyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hasan.bestclothesforyou.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException
import java.time.LocalDateTime

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
            .url("https://api.tomorrow.io/v4/weather/forecast?apikey=L4sJOkIDWYiG1IRwLPNkjYHgzq3GRgrq&location=egypt")
            .build()
        client.newCall(requestWeatherApi).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO(e.toString() )
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    binding.textView.text = LocalDateTime.now().toString()

                }
            }

        })
    }

}