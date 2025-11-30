package com.samadhan.sdk


import android.util.Log

object SdkInitializer {

    var baseUrl: String? = null
    var poleBaseUrl: String? = null
        private set

    fun init(baseUrl: String,poleBaseUrl:String) {
        this.baseUrl = baseUrl
        this.poleBaseUrl = poleBaseUrl
        Log.d("SdkInitializer", "SDK initialized with baseUrl=$baseUrl")
    }
}
