package com.hasan.bestclothesforyou.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hasan.bestclothesforyou.R
import com.hasan.bestclothesforyou.databinding.ActivityMainBinding
import com.hasan.bestclothesforyou.ui.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private lateinit var handler: Handler
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
    }
    fun initFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, homeFragment)
        transaction.commit()
    }
    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
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