package com.whatsycrrop.dpmaker.utiles

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.lang.NullPointerException

//import com.google.gson.Gson;
class TinyDB(appContext: Context) {
    private val context: Context
    private val preferences: SharedPreferences
    private val DEFAULT_APP_IMAGEDATA_DIRECTORY: String? = null
    private val lastImagePath = ""
    fun getInt(key: String?): Int {
        return preferences.getInt(key, 0)
    }

    fun putInt(key: String?, value: Int) {
        checkForNullKey(key)
        preferences.edit().putInt(key, value).apply()
    }

    private fun checkForNullKey(key: String?) {
        if (key == null) {
            throw NullPointerException()
        }
    }

    init {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext)
        context = appContext
    }
}