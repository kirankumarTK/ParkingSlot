package com.example.parkingslot.utils

import android.util.Log
import com.example.parkingslot.BuildConfig
import javax.inject.Inject

class LoggerUtils @Inject constructor() {

    fun debug(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg)
    }

    fun error(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.e(tag, msg)
    }

    fun info(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.i(tag, msg)
    }
}