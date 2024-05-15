package com.jkontodos.cabifystore.data.sharedpreferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jkontodos.cabifystore.data.source.LocalDataSource
import javax.inject.Inject

class SharedPreferencesDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : LocalDataSource {

}