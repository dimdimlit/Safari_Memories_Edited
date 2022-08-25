package com.example.mysafaritestapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mysafaritestapp.databinding.ActivityHomeBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding
    lateinit var imageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Changed
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mBtnSelectImg.setOnClickListener {

            selectImage()
        }

        binding.mBtnUploadImg.setOnClickListener {

            uploadImage()
        }
    }

    private fun uploadImage() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading file...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_mm_dd_HH_mm_ss", Locale.getDefault())
        val now = Date ()
        val fileName = formatter.format(now)

        //Storage reference Variable (FirebaseStorage)

          val  storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
            storageReference.putFile(imageUri).
                    addOnSuccessListener {

                        binding.mImgHome.setImageURI(null)
                        Toast.makeText(this@HomeActivity,"Memory Upload Successful"
                        ,Toast.LENGTH_SHORT).show()
                        if (progressDialog.isShowing) progressDialog.dismiss()

                    }.addOnFailureListener{

                        if (progressDialog.isShowing) progressDialog.dismiss()
                     Toast.makeText(this@HomeActivity,"Memory Upload Failed"
                    ,Toast.LENGTH_SHORT).show()

        }


    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "images/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK)

            imageUri = data?.data!!
            binding.mImgHome.setImageURI(imageUri)

    }
}