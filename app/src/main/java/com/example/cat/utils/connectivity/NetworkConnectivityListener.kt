package com.example.cat.utils.connectivity

interface NetworkConnectivityListener {
    fun onConnected()

    fun onDisconnected()
}