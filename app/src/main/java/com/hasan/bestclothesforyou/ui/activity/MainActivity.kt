package com.hasan.bestclothesforyou.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hasan.bestclothesforyou.databinding.ActivityMainBinding
import com.hasan.bestclothesforyou.ui.adapter.PagerAdapter
import com.hasan.bestclothesforyou.ui.fragment.AddClothesFragment
import com.hasan.bestclothesforyou.ui.fragment.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val addClothesFragment = AddClothesFragment()
    private val listOfFragment = listOf(homeFragment, addClothesFragment)
    private lateinit var handler: Handler
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
    }
    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
private fun initViewPager(){
    val myViewPager = PagerAdapter(this, listOfFragment)
    binding.viewPager.adapter = myViewPager
}

}
//        handler = Handler(Looper.getMainLooper())
////        getInit()
//        handler.postDelayed(object : Runnable {
//            override fun run() {
//                homeFragment.binding.textCurrentTime.text = getCurrentTime().removeRange(5, getCurrentTime().length)
//                handler.postDelayed(this, 36000000)
//            }
//        }, 1)