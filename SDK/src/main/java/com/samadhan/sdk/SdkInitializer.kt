package com.samadhan.sdk


import android.util.Log

object SdkInitializer {

    var baseUrl: String? = null
        private set

    fun init(baseUrl: String) {
        this.baseUrl = baseUrl
        Log.d("SdkInitializer", "SDK initialized with baseUrl=$baseUrl")
    }
}
