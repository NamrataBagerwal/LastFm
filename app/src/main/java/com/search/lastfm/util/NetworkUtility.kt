package com.search.lastfm.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

object NetworkUtility {

    fun isNetworkAvailable(context: Context): Boolean?{
        val connectivityManager: ConnectivityManager = context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: Network? = connectivityManager.activeNetwork
        val networkCapabilities: NetworkCapabilities?=  connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}