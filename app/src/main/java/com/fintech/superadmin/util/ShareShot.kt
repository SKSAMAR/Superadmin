package com.fintech.superadmin.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.graphics.applyCanvas
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*



fun ComponentActivity.shareScreen(bmp: Bitmap){
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        val fileScreenShot = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            Calendar.getInstance().time.toString() + ".jpg"
        )
        try {
            val fileOutputStream = FileOutputStream(fileScreenShot)
            bmp.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                fileOutputStream
            )
            fileOutputStream.flush()
            fileOutputStream.close()
            share(bmp)
        } catch (e: Exception) {
            showToast(
                fileScreenShot.toString() + "First Exception.. " + e.localizedMessage
            )
        }
    }, 1000)
}


fun ComponentActivity.captureScreen(bmp: Bitmap) {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        val fileScreenShot = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            Calendar.getInstance().time.toString() + ".jpg"
        )
        try {
            val fileOutputStream = FileOutputStream(fileScreenShot)
            bmp.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                fileOutputStream
            )
            fileOutputStream.flush()
            fileOutputStream.close()
            this.download(bmp)
        } catch (e: Exception) {
            showToast(
                fileScreenShot.toString() + "First Exception.. " + e.localizedMessage
            )
        }
    }, 1000)
}


fun View.toBitmap(activity: Activity, onBitmapReady: (Bitmap) -> Unit) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val temporalBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)

            // Above Android O, use PixelCopy due
            // https://stackoverflow.com/questions/58314397/
            val window: Window = activity.window

            val location = IntArray(2)

            this.getLocationInWindow(location)

            val viewRectangle = Rect(location[0], location[1], location[0] + this.width, location[1] + this.height)

            val onPixelCopyListener: PixelCopy.OnPixelCopyFinishedListener = PixelCopy.OnPixelCopyFinishedListener { copyResult ->

                if (copyResult == PixelCopy.SUCCESS) {

                    onBitmapReady(temporalBitmap)
                } else {

                    error("Error while copying pixels, copy result: $copyResult")
                }
            }

            PixelCopy.request(window, viewRectangle, temporalBitmap, onPixelCopyListener, Handler(Looper.getMainLooper()))
        } else {

            val temporalBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.RGB_565)

            val canvas = Canvas(temporalBitmap)

            this.draw(canvas)

            canvas.setBitmap(null)

            onBitmapReady(temporalBitmap)
        }

    } catch (exception: Exception) {
        exception.printStackTrace()
    }
}


fun getBitmapFromView(view: View, height: Int, width: Int): Bitmap? {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val bgDrawable = view.background
    if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
    view.draw(canvas)
    return bitmap
}

fun ComponentActivity.shareScreen(view: View){
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        val bmp = Bitmap.createBitmap(view.rootView.width, view.rootView.height,
            Bitmap.Config.ARGB_8888).applyCanvas {
            view.draw(this)
        }
        val fileScreenShot = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            Calendar.getInstance().time.toString() + ".jpg"
        )
        try {
            val fileOutputStream = FileOutputStream(fileScreenShot)
            bmp.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                fileOutputStream
            )
            fileOutputStream.flush()
            fileOutputStream.close()
            share(bmp)
        } catch (e: Exception) {
            showToast(
                fileScreenShot.toString() + "First Exception.. " + e.localizedMessage
            )
        }
    }, 1000)
}


fun ComponentActivity.captureScreen(view: View){
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        val bmp = Bitmap.createBitmap(view.width, view.height,
            Bitmap.Config.ARGB_8888).applyCanvas {
            view.draw(this)
        }
        val fileScreenShot = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            Calendar.getInstance().time.toString() + ".jpg"
        )
        try {
            val fileOutputStream = FileOutputStream(fileScreenShot)
            bmp.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                fileOutputStream
            )
            fileOutputStream.flush()
            fileOutputStream.close()
            this.download(bmp)
        } catch (e: Exception) {
            showToast(
                fileScreenShot.toString() + "First Exception.. " + e.localizedMessage
            )
        }
    }, 1000)
}


private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}

fun ComponentActivity.getImageUri(inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        contentResolver,
        inImage,
        "IMG_" + Calendar.getInstance().time,
        null
    )
    return Uri.parse(path)
}

fun ComponentActivity.share(bitmap: Bitmap) {
    val uri: Uri = getImageUri(bitmap)
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "image/*"
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    startActivity(Intent.createChooser(shareIntent, "Share receipt"))
}

fun ComponentActivity.download(bitmap: Bitmap) {
    val uri: Uri = getImageUri(bitmap)
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "image/*"
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    showToast("Image Downloaded")
    //startActivity(Intent.createChooser(shareIntent, "Share receipt"))
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}