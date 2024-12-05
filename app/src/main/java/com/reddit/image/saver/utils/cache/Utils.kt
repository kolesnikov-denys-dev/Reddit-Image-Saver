package com.reddit.image.saver.utils.cache

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object Utils {
    fun copyStream(input: InputStream, output: OutputStream) {
        try {
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (true) {
                bytesRead = input.read(buffer)
                if (bytesRead == -1) break
                output.write(buffer, 0, bytesRead)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            Log.e("Utils", "Error copying stream", ex)
        } finally {
            try {
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                output.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}