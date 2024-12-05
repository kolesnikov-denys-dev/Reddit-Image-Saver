package com.reddit.image.saver.screens.image

import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.reddit.image.saveraver.R
import com.reddit.image.saver.utils.cache.ImageLoader
import com.reddit.image.saver.utils.common.Constants.IMAGE_QUALITY
import com.reddit.image.saver.utils.common.Constants.KEY_IMAGE_URL
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ImageActivity : AppCompatActivity() {
    private lateinit var imageViewOpen: ImageView
    private lateinit var buttonSaveToGallery: Button
    var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        imageViewOpen = findViewById(R.id.imageViewOpenImage)
        buttonSaveToGallery = findViewById(R.id.buttonSaveToGallery)
        val imageUrl = intent.getStringExtra(KEY_IMAGE_URL)
        val imgLoader = ImageLoader(this)
        imgLoader.displayImage(imageUrl!!, R.drawable.ic_reddit, imageViewOpen)
        bitmap = imgLoader.getBitmap(imageUrl)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun saveImage(view: View) {
        bitmap?.let { saveImage(it) }
    }

    private fun saveImage(finalBitmap: Bitmap) {
        val root = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        ).toString()
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val n = Random().nextInt(10000)
        val fName = "Image-$n.jpg"
        val file = File(myDir, fName)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        MediaScannerConnection.scanFile(
            this, arrayOf(file.toString()), null
        ) { path, uri ->
            Log.i("ExternalStorage", "Scanned $path:")
            Log.i("ExternalStorage", "-> uri=$uri")
        }
    }
}