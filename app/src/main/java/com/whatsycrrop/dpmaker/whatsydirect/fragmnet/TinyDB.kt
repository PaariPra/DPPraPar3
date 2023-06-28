package com.chetsapp.whatsydirect.fragmnet

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class TinyDB (appContext: Context) {

    private val context: Context
    private val preferences: SharedPreferences
    private val DEFAULT_APP_IMAGEDATA_DIRECTORY: String? = null
    private val lastImagePath = ""


    fun getInt(key: String?): Int {
        return preferences.getInt(key, 0)
    }

    fun getString(key: String): String? {
        return preferences.getString(key,"")
    }

    fun putInt(key: String?, value: Int) {
        checkForNullKey(key)
        preferences.edit().putInt(key, value).apply()
    }

    fun putString(key: String?, value: String) {
        checkForNullKey(key)
        preferences.edit().putString(key, value).apply()
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