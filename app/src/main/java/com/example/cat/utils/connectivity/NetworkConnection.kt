package com.example.cat.utils.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

object NetworkConnection: NetworkConnectivityListener {
    private val TAG = NetworkConnection::class.simpleName

    var isConnected: Boolean = false
        private set

    fun isWifiConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }

    override fun onConnected() {
        updateConnectionState(true)
    }

    override fun onDisconnected() {
        updateConnectionState(false)
    }

    private fun updateConnectionState(isConnected: Boolean) {
        Log.d(TAG, "isNetworkConnected: $isConnected")
        NetworkConnection.isConnected = isConnected
    }
}