package com.reddit.image.saver.utils.cache

import android.content.Context
import java.io.File

class FileCache(context: Context) {
    private val cacheDir: File = context.cacheDir

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    fun getFile(url: String): File {
        val filename = url.hashCode().toString()
        return File(cacheDir, filename)
    }

    fun clear() {
        val files = cacheDir.listFiles()
        if (files != null) {
            for (file in files) {
                file.delete()
            }
        }
    }
}