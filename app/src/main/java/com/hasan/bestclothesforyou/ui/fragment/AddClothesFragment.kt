package com.hasan.bestclothesforyou.ui.fragment

import android.R
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.hasan.bestclothesforyou.data.Season
import com.hasan.bestclothesforyou.databinding.FragmentAddClothesBinding
import com.google.android.material.button.MaterialButton
import com.hasan.bestclothesforyou.ui.adapter.SeasonAdapter
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

@Suppress("DEPRECATION")
class AddClothesFragment : Fragment() {
    private lateinit var binding : FragmentAddClothesBinding
    private val CAMERA_REQUEST_CODE = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val seasonAdapter = SeasonAdapter(seasons)
        binding = FragmentAddClothesBinding.inflate(inflater, container, false)
        binding.recyclerSeason.adapter = seasonAdapter
        createAdapter()
        return binding.root
    }

    private fun createAdapter(){
        val spinner = binding.spinnerClothes
        val items = arrayOf("T-shirt", "suit", "cote")
        val adapter = ArrayAdapter(binding.spinnerClothes.context, R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter}

    private companion object{
        private val  seasons = listOf(
            Season("winter", false) ,
            Season("summer", false),
            Season("autumn", false),
            Season("spring", false),
        )
    }
    fun cameraCheckPermission(){
        Dexter.withContext(this.context).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener{
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (report.areAllPermissionsGranted()){
                        camera()
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

}