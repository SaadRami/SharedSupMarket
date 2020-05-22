package be.supinfo.supermarketapp.util

import android.content.Context

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
    }
}