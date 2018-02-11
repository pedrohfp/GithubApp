package br.com.githubapp.data.model

/**
 * Created by pedrohenrique on 10/02/2018.
 */

enum class Status{
    SUCCESS, ERROR, LOADING
}

data class Resource<T>(val status: Status, val data: T? = null, val message: String? = null)