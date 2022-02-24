package com.ebookfrenzy.cameraapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

private const val REQUEST_CODE = 1
private lateinit var myPhoto: File
private var myPhotoName = "photo"



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btnTakePicture).setOnClickListener {
            val intentPics = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            myPhoto = getPhotoFromCamera(myPhotoName)
//          security check
            val newProvider =
                FileProvider.getUriForFile(this, "com.ebookfrenzy.cameraapp.fileprovider", myPhoto)
            intentPics.putExtra(MediaStore.EXTRA_OUTPUT, newProvider)
            if (intentPics.resolveActivity(this.packageManager) != null) {
                @Suppress("DEPRECATION")
                startActivityForResult(intentPics, REQUEST_CODE)
            } else {
//                There is an error
                Toast.makeText(this, "No Camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPhotoFromCamera(fileName: String): File {
        val localDirectory: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jgp", localDirectory)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val LQimg:Bitmap = data?.extras?.get("data") as Bitmap
            val HQimg: Bitmap = BitmapFactory.decodeFile(myPhoto.absolutePath)
            findViewById<ImageView>(R.id.imageView).setImageBitmap(HQimg)
            super.onActivityResult(requestCode, resultCode, data)

        }
    }
}