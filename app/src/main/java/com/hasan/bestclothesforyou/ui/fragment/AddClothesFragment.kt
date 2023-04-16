package com.hasan.bestclothesforyou.ui.fragment

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import coil.load
import com.hasan.bestclothesforyou.data.ClothesData
import com.hasan.bestclothesforyou.data.Season
import com.hasan.bestclothesforyou.database.ClothesDatabaseHelper
import com.hasan.bestclothesforyou.databinding.FragmentAddClothesBinding
import com.hasan.bestclothesforyou.ui.adapter.SeasonAdapter
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.lang.Math.random

@Suppress("DEPRECATION")
class AddClothesFragment : Fragment() {
    private lateinit var binding : FragmentAddClothesBinding
    private val CAMERA_REQUEST_CODE = 1
    lateinit var imageBitmap: Bitmap
    private lateinit var databaseHelper : ClothesDatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val seasonAdapter = SeasonAdapter(seasons)
        binding = FragmentAddClothesBinding.inflate(inflater, container, false)
        binding.recyclerSeason.adapter = seasonAdapter
        createAdapter()
        databaseHelper = ClothesDatabaseHelper(requireContext())
        binding.apply {
            editTextNameClothes.addTextChangedListener {
                textViewTitleCardAddNewClothes.text = editTextNameClothes.text
            }
        }
        binding.spinnerClothes.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.textViewCategoryUpload.text = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.textViewCategoryUpload.text = "Category"
            }
        })

        binding.imageViewUploadImage.setOnClickListener {
            cameraCheckPermission()
        }
        binding.apply {

            addToMyCollection.setOnClickListener {
                val nameOfClothes = editTextNameClothes.text.toString()
                val category = spinnerClothes.selectedItem.toString()
                val season = "summer"
                val weatherDegree = Pair(0, 50)
                val id = random() * 10000
                val image = imageBitmap
                val newClothes = ClothesData(
                    id = id.toString(),
                    name = nameOfClothes,
                    category = category,
                    seasonType = season,
                    weatherDegree = weatherDegree,
                    imageBitmap = image
                )
                databaseHelper.addClothes(newClothes)
                Log.i("Database111","${databaseHelper.getAllClothes()}")

            }
        }
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            val bitmap = data?.extras?.get("data") as Bitmap
            val sharePreprences = requireActivity().getSharedPreferences("Name", Context.MODE_PRIVATE)
            val editor = sharePreprences.edit()
            val imageBitMap = bitmap
            editor.putString("Name", bitmap.toString())
            editor.apply()
            binding.imageViewUploadImage.load(bitmap)
            getBitmap(bitmap)
    }
    fun getBitmap(bitmap : Bitmap){
        imageBitmap = bitmap
    }



    private fun createAdapter(){
        val spinner = binding.spinnerClothes
        val items = arrayOf("T-shirt", "suit", "cote")
        val adapter = ArrayAdapter(binding.spinnerClothes.context, R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

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
                showRotationDialogPermission()
            }

        }).onSameThread().check()
    }

    private fun showRotationDialogPermission() {
        AlertDialog.Builder(this.context).setMessage(
        "it look like you have turned off permission"+
        "required for this feature, it can be enable under app setting"
        )
            .setPositiveButton("Go To Setting"){_,_->

                    try {   Log.i("AAA", "AAA")
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        Log.i("BBB", "BBB")
                        val url = Uri.fromParts("package", "com.hasan.bestclothesforyou", null)
                        Log.i("CCC", "CCC")
                        intent.data = url
                        Log.i("DDD", "DDD")
                        startActivity(intent)
                        Log.i("EEE", "EEE")
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                        Log.i("catch", e.printStackTrace().toString())
                }

            }.setNegativeButton("Cancle",){dialog,_->
                run {
                    dialog.dismiss()
                }
            }.show()
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }
    private companion object{
        private val  seasons = listOf(
            Season("winter", false) ,
            Season("summer", false),
            Season("autumn", false),
            Season("spring", false),
        )
    }
}