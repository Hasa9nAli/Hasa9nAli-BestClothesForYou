package com.hasan.bestclothesforyou.ui.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.hasan.bestclothesforyou.databinding.FragmentAddClothesBinding

class AddClothesFragment : Fragment() {
    private val season = listOf(
        "winter",
        "summer",
        "spring",
        "autumn"
    )
    private lateinit var binding : FragmentAddClothesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddClothesBinding.inflate(inflater, container, false)
        createAdapter()
        return binding.root
    }

    private fun createAdapter(){
        val spinner = binding.spinnerClothes
        val items = arrayOf("T-shirt", "suit", "cote")
        val adapter = ArrayAdapter(binding.spinnerClothes.context, R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter}
}