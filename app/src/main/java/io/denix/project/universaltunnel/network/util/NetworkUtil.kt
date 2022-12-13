package io.denix.project.universaltunnel.network.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager

object NetworkUtil {

    fun isConnected(context: Context?): Boolean {
        return isNetworkConnected(context) || isWifiConnected(context)
    }

    private fun isWifiConnected(context: Context?): Boolean {
        return context?.let {
            val mConnectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            mConnectivityManager.activeNetworkInfo?.isAvailable
        }?: false
    }

    private fun isNetworkConnected(context: Context?): Boolean {
        return context?.let {
            val mConnectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isAvailable
        }?: false
    }
}