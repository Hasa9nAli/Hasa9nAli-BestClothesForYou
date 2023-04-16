package com.hasan.bestclothesforyou.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airbnb.lottie.L
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hasan.bestclothesforyou.R
import com.hasan.bestclothesforyou.data.ClothesData
import com.hasan.bestclothesforyou.data.WeatherData
import com.hasan.bestclothesforyou.data.getAllClothes
import com.hasan.bestclothesforyou.databinding.FragmentHomeBinding
import com.hasan.bestclothesforyou.util.Constraint
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class HomeFragment : Fragment() {
    private val client = OkHttpClient()
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        makeRequestOfWeatherApiOKHTTP()
        return binding.root
    }

    private fun makeRequestOfWeatherApiOKHTTP() {
        val requestWeatherApi = Request.Builder()
            .url("http://api.weatherstack.com/current?access_key=${Constraint.API_WEATHER_KEY}&query=${Constraint.LOCATION_CITY_WEATHER}")
            .build()
        client.newCall(requestWeatherApi).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val sharedPreferences = requireActivity().getSharedPreferences(DATE_INFORMATION, Context.MODE_PRIVATE)
                val responseApi = response.body?.string()
                val gson = Gson()
                val weatherData = gson.fromJson(responseApi, WeatherData::class.java)
                val localTime = weatherData.location.localtime.substring(5..9)
                val currentTime = getCurrentLocalDate()
                val chooseClothesForToday = filterClothesDataBySeasonAndTemperature(
                    getAllClothes(),
                    "summer",
                    weatherData.current.temperature.toInt()
                )
                requireActivity().runOnUiThread {
                    setComponentOnFragment(weatherData, chooseClothesForToday)
                }
            }
        })
    }

    fun getCurrentLocalDate(): String {
        val calendar = Calendar.getInstance()
        val timeZone = TimeZone.getTimeZone("UTC")
        val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
        dateFormat.timeZone = timeZone
        return dateFormat.format(calendar.time)
    }

    private fun uploadImage(chooseClothesForToday: List<ClothesData>) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(DATE_INFORMATION, Context.MODE_PRIVATE)
        val storeIdImage = sharedPreferences.getString("idClothesToday","")
        Glide.with(binding.imageViewSuggestClothes.context).load(chooseClothesForToday.filter {it.id == storeIdImage}[0].imageUrl)
            .placeholder(R.drawable.ellipse_temp)
            .into(binding.imageViewSuggestClothes)
    }

    private fun setComponentOnFragment(
        weatherData: WeatherData,
        chooseClothesForToday: List<ClothesData>
    ) {
        binding.apply {
            textCurrentTime.text = LocalTime.now().toString().removeRange(
                5,
                LocalTime.now().toString().length
            )
            textViewLocation.text = weatherData.location.name + ", " + weatherData.location.country
            textTemperatureCard.text = weatherData.current.temperature.toInt().toString()
            textViewWeatherStatus.text = weatherData.current.weatherDescriptions[0]
            textViewHistory.text =
                weatherData.location.localtime /*"${LocalDate.now().month.toString().lowercase()}-${LocalDate.now().dayOfMonth}-${LocalDate.now().year}"*/

        }
    }

    fun filterClothesDataBySeasonAndTemperature(
        clothesList: List<ClothesData>,
        season: String,
        temperature: Int
    ): List<ClothesData> {
        return clothesList.filter { clothesData ->
            clothesData.seasonType == season &&
                    temperature in clothesData.weatherDegree.first..clothesData.weatherDegree.second
        }
    }
    fun addToSharedPreferences(key: String, dayOfThisClothes: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(DATE_INFORMATION, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, dayOfThisClothes)
        editor.apply()
    }
    fun removeFromSharedPreferences(name: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(DATE_INFORMATION, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(name)
        editor.apply()
    }
    companion object {
        const val DATE_INFORMATION = "Date Information"
    }
}