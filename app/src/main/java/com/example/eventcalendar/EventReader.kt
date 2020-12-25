package com.example.eventcalendar

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

class EventReader {
    private val path = "events.json"

    private val gson = Gson()
    private val type: Type = object : TypeToken<MutableList<EventItem>>() {}.type

    fun readFromFile(context: Context): MutableList<EventItem> {
        lateinit var string: String

        if (!fileExist(context, path)) {
            try {
                val inputStream: InputStream = context.assets.open(path)
                val buffer = ByteArray(inputStream.available())
                inputStream.read(buffer)
                string = String(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            string = BufferedReader(InputStreamReader(context.openFileInput(path))).readLine().toString()
        }
        return gson.fromJson(string, type)
    }

    fun writeToFile(context: Context, list: List<EventItem>) {
        val jsonString = gson.toJson(list)
        try {
            val fileOutputStream = context.openFileOutput(path, Context.MODE_PRIVATE)
            fileOutputStream.write(jsonString.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun fileExist(context: Context, path: String): Boolean {
        val file = context.getFileStreamPath(path)
        return file.exists()
    }
}