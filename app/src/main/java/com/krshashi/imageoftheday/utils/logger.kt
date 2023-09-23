package com.krshashi.imageoftheday.utils

import android.util.Log

fun logger(vararg args: Any) {
    Log.d("observe", args.joinToString())
}