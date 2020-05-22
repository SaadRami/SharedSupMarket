package be.supinfo.supermarketapp.util

import android.content.Context

class MyHelper {
    companion object {
        //LOGIC
        fun blFunction(): String = "Test"

        fun getDataFromResourceFile(context: Context, resId: Int): String {
            return context.resources.openRawResource(resId).use {
                it.bufferedReader().use { it.readText() }
            }
        }
    }
}