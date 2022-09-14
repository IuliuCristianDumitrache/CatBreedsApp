package com.example.cat.data

import android.content.SharedPreferences

class SessionManager(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"

    }

    fun setAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return getAccessToken().isNotEmpty()
    }

    private fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, "")!!
    }
}