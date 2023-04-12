package com.hasan.bestclothesforyou.ui


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(container : FragmentActivity, private val listOfFragment : List<Fragment>) : FragmentStateAdapter(container) {

    override fun getItemCount(): Int = listOfFragment.size

    override fun createFragment(position: Int) = listOfFragment[position]
}