package com.ps.test.code.mobilesdeexercise.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class Utilities {
    companion object {
        fun getJsonFromAssets(context: Context, fileName: String): String? {
            val jsonString: String = try {
                val `is`: InputStream = context.assets.open(fileName)
                val size: Int = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer, Charset.forName("UTF-8"))
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return jsonString
        }
    }
}