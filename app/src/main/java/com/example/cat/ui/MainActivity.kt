package com.example.cat.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.cat.databinding.MainActivityBinding
import com.example.cat.network.RxBus
import com.example.cat.network.rxmessages.ReloadCatList
import com.example.cat.utils.connectivity.NetworkCallback
import com.example.cat.utils.connectivity.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val networkConnection = NetworkConnection
    lateinit var views: MainActivityBinding

    //TODO - splash screen to check if user already loggedin
    private val networkCallback = object : NetworkCallback(networkConnection) {
        override fun onAvailable(network: Network) {
            runOnUiThread {
                views.viewNoInternet.isVisible = false
            }
            RxBus.publish(ReloadCatList(true))
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            runOnUiThread {
                views.viewNoInternet.isVisible = true
            }
            super.onLost(network)
        }
    }

    private fun registerNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
    }

    private fun unregisterNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = MainActivityBinding.inflate(layoutInflater)
        setContentView(views.root)
        registerNetworkCallback()
    }

    override fun onDestroy() {
        unregisterNetworkCallback()
        super.onDestroy()
    }
}