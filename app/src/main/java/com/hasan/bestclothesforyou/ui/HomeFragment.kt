package com.hasan.bestclothesforyou.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hasan.bestclothesforyou.R
import com.hasan.bestclothesforyou.data.ClothesData
import com.hasan.bestclothesforyou.data.WeatherData
import com.hasan.bestclothesforyou.data.getAllClothes
import com.hasan.bestclothesforyou.databinding.FragmentHomeBinding
import com.hasan.bestclothesforyou.util.Constraint
import com.hasan.bestclothesforyou.util.PrefUtil
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
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
        PrefUtil.initialPref(requireContext())
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
                Log.i("Hasan", "Fail request")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseApi = response.body?.string()
                val gson = Gson()
                val weatherData = gson.fromJson(responseApi, WeatherData::class.java)
                val localTime = weatherData.location.localtime.substring(5..9)
                val currentTime = getCurrentLocalDate()
                val chooseClothesForToday = filterClothesDataBySeasonAndTemperature(
                    getAllClothes(),
                    weatherData.current.temperature.toInt()
                )
                requireActivity().runOnUiThread {
                    setComponentOnFragment(weatherData)
                    if (localTime != currentTime) {
                        val yesterdayClothesId = PrefUtil.idOfClothes
                        val clothesToday = chooseClothesForToday
                            .filter{it.id != yesterdayClothesId}.random()
                        Glide.with(binding.imageViewSuggestClothes.context)
                            .load(clothesToday.imageUrl)
                            .placeholder(R.drawable.ellipse_temp)
                            .into(binding.imageViewSuggestClothes)
                    }else{
                        filterAndUploadImage(chooseClothesForToday)
                    }
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

    private fun filterAndUploadImage(chooseClothesForToday: List<ClothesData>) {
        val storeIdImage = PrefUtil.idOfClothes
        val filteredClothesAndGetImage = storeIdImage?.let { filterClothesById(chooseClothesForToday, it) }
        Glide.with(binding.imageViewSuggestClothes.context).load(filteredClothesAndGetImage?.imageUrl)
            .placeholder(R.drawable.ellipse_temp)
            .into(binding.imageViewSuggestClothes)
    }
    fun filterClothesById(chooseClothesForToday: List<ClothesData>, storeIdImage: String)
       = chooseClothesForToday.filter {it.id == storeIdImage}[0]



    private fun setComponentOnFragment(
        weatherData: WeatherData
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
                weatherData.location.localtime
        }
    }

    fun filterClothesDataBySeasonAndTemperature(
        clothesList: List<ClothesData>,
        temperature: Int
    ): List<ClothesData> = clothesList.filter { clothesData ->
            temperature in clothesData.weatherDegree.first..clothesData.weatherDegree.second }

    companion object {
        const val DATE_INFORMATION = "Date Information"
        const val CLOTHES_ID = "idClothesToday"
    }
}