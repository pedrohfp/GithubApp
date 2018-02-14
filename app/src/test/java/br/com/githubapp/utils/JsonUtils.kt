package br.com.githubapp.utils

import android.content.Context
import java.io.File
import kotlin.reflect.KClass

/**
 * Created by pedrohenrique on 14/02/2018.
 */
fun getJson(kclass: Any, path: String): String{
    val uri = kclass.javaClass.classLoader.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}