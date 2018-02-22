package com.example.mocks.utils

import java.io.File

/**
 * Created by pedro on 21/02/18.
 */
fun getJson(kclass: Any, path: String): String{
    val uri = kclass.javaClass.classLoader.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}