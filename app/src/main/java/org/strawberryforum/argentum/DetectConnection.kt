package org.strawberryforum.argentum

import android.content.Context
import android.net.ConnectivityManager

object DetectConnection {
    fun checkInternetConnection(context: Context): Boolean {
        val conManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return conManager.activeNetworkInfo?.let {
            it.isAvailable && it.isConnected
        } ?: false
    }
}