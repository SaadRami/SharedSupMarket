package be.supinfo.supermarketapp.util

import android.content.Context
import android.content.SharedPreferences
import java.io.File

const val ITEM_STRING = "item_string"


class MyHelper {

    companion object {
        //LOGIC
        fun blFunction(): String = "Test"

        fun getDataFromResources(context: Context, resId: Int): String {
            return context.resources.openRawResource(resId).use {
                it.bufferedReader().use { it.readText() }
            }
        }

        fun getDataFromAssets(context: Context, fileName: String): String {
            return context.assets.open(fileName).use {
                it.bufferedReader().use { it.readText() }
            }
        }

        fun saveTextToFile(app: Context, data: String?) {
            //     val file = File(app.filesDir, CACHE_DIR)
            //     val file = File(app.cacheDir, CACHE_DIR)
            val file = File(app.getExternalFilesDir("products"), CACHE_DIR)
            file.writeText(data ?: "", Charsets.UTF_8)
        }

        fun readTextFile(app: Context): String? {
            val file = File(app.filesDir, CACHE_DIR)
            return if (file.exists()) {
                file.readText()
            } else {
                null
            }
        }


        // 0 -> this is available anywhere
        // the string will be used as the name of the xml file
        // each preference object represents one xml file
        private fun preferences(context: Context): SharedPreferences =
            context.getSharedPreferences("default", 0)

        fun setDisplayMode(context: Context, data: String) {
            preferences(context).edit().putString(ITEM_STRING, data).apply()
        }

        fun getDisplayMode(context: Context): String =
            preferences(context).getString(ITEM_STRING, "displayMode")!!


    }
}