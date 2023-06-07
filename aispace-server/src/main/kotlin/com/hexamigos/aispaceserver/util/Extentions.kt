package com.hexamigos.aispaceserver.util

import com.google.gson.Gson

fun <T> T.toJson(): String? {
    val gson = Gson()
    return gson.toJson(this)
}