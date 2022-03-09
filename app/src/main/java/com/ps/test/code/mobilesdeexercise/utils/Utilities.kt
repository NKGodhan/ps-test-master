package com.ps.test.code.mobilesdeexercise.utils

import android.content.Context
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/***
 * Utility class for the app
 */
class Utilities {
    companion object {
        val ASSETS_JSON_FILE_NAME = "server_metadata.json"

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

        fun showToastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}